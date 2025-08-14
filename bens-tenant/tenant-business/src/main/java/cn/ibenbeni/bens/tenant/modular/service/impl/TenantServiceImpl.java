package cn.ibenbeni.bens.tenant.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.util.DateUtils;
import cn.ibenbeni.bens.sys.api.PermissionApi;
import cn.ibenbeni.bens.sys.api.SysRoleServiceApi;
import cn.ibenbeni.bens.sys.api.SysUserServiceApi;
import cn.ibenbeni.bens.sys.api.constants.SysConstants;
import cn.ibenbeni.bens.sys.api.enums.role.RoleCodeEnum;
import cn.ibenbeni.bens.sys.api.enums.role.RoleTypeEnum;
import cn.ibenbeni.bens.sys.api.pojo.role.dto.RoleDTO;
import cn.ibenbeni.bens.sys.api.pojo.role.dto.RoleSaveReqDTO;
import cn.ibenbeni.bens.tenant.api.callback.RemoveTenantCallbackApi;
import cn.ibenbeni.bens.tenant.api.exception.TenantException;
import cn.ibenbeni.bens.tenant.api.exception.enums.TenantExceptionEnum;
import cn.ibenbeni.bens.tenant.api.util.TenantUtils;
import cn.ibenbeni.bens.tenant.modular.convert.TenantConvert;
import cn.ibenbeni.bens.tenant.modular.entity.TenantDO;
import cn.ibenbeni.bens.tenant.modular.entity.TenantPackageDO;
import cn.ibenbeni.bens.tenant.modular.mapper.TenantMapper;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantPageReq;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantSaveReq;
import cn.ibenbeni.bens.tenant.modular.service.TenantPackageService;
import cn.ibenbeni.bens.tenant.modular.service.TenantService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 租户服务实现类
 *
 * @author: benben
 * @time: 2025/7/1 上午11:06
 */
@Slf4j
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, TenantDO> implements TenantService {

    // region 属性

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private SysRoleServiceApi roleServiceApi;

    @Resource
    private PermissionApi permissionApi;

    @Resource
    private SysUserServiceApi userServiceApi;

    @Lazy
    @Resource
    private TenantPackageService tenantPackageService;

    // endregion

    // region 公共方法

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public Long createTenant(TenantSaveReq saveReq) {
        // 验证租户名称是否重复
        validTenantNameDuplicate(null, saveReq.getTenantName());
        // 验证租户域名是否重复
        validTenantWebsiteDuplicate(null, saveReq.getTenantWebsite());
        // 验证租户套餐是否存在或禁用
        TenantPackageDO tenantPackage = tenantPackageService.validTenantPackage(saveReq.getTenantPackageId());

        TenantDO tenant = BeanUtil.toBean(saveReq, TenantDO.class);
        save(tenant);

        // 创建租户管理员角色和管理员用户
        TenantUtils.execute(tenant.getTenantId(), () -> {
            // 创建租户管理员角色
            Long roleId = createRole(tenantPackage);
            // 创建租户管理员用户
            Long userId = createUser(roleId, saveReq);

            // 更新租户管理用户ID
            TenantDO updateTenant = TenantDO.builder()
                    .tenantId(tenant.getTenantId())
                    .contactUserId(userId)
                    .build();
            updateById(updateTenant);
        });

        return tenant.getTenantId();
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteTenant(Long tenantId) {
        TenantUtils.execute(tenantId, () -> {
            // 校验是否允许更新
            validateUpdateTenant(tenantId);
            baseRemoveTenant(CollUtil.set(false, tenantId));
        });
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteTenant(Set<Long> tenantIdSet) {
        tenantIdSet.forEach(this::deleteTenant);
    }

    @Override
    public void updateTenant(TenantSaveReq updateReq) {
        // 校验是否允许更新
        TenantDO tenant = validateUpdateTenant(updateReq.getTenantId());
        // 验证租户名称是否重复
        validTenantNameDuplicate(updateReq.getTenantId(), updateReq.getTenantName());
        // 验证租户域名是否重复
        validTenantWebsiteDuplicate(updateReq.getTenantId(), updateReq.getTenantWebsite());
        // 验证租户套餐是否存在或禁用
        TenantPackageDO tenantPackage = tenantPackageService.validTenantPackage(updateReq.getTenantPackageId());
        Long oldPackageId = tenant.getTenantPackageId();


        BeanUtil.copyProperties(updateReq, tenant);
        updateById(tenant);

        // 若租住套餐变化，则修改租户的菜单权限
        if (ObjectUtil.notEqual(oldPackageId, updateReq.getTenantPackageId())) {
            updateTenantMenu(tenant.getTenantId(), tenantPackage.getPackageMenuIds());
        }
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void updateTenantMenu(Long tenantId, Set<Long> menuIdSet) {
        // 使用指定租户ID执行操作
        TenantUtils.execute(tenantId, () -> {
            // 获取租户的所有角色
            List<RoleDTO> roleList = roleServiceApi.list();
            roleList.forEach(role -> Assert.isTrue(tenantId.equals(role.getTenantId()), "角色ID:{},角色租户ID:{},实际租户:{}", role.getRoleId(), role.getTenantId(), tenantId));
            // 更新角色的菜单权限
            roleList.forEach(role -> {
                // 若是租户管理员角色，则更新菜单权限
                if (ObjectUtil.equal(role.getRoleCode(), RoleCodeEnum.TENANT_ADMIN.getCode())) {
                    permissionApi.assignRoleMenu(role.getRoleId(), menuIdSet);
                }
                // 如果是其余角色，则取消超出权限
                Set<Long> roleMenuIdSet = permissionApi.listRoleMenuByRoleId(role.getRoleId());
                // intersectionDistinct：计算集合的交集
                roleMenuIdSet = CollUtil.intersectionDistinct(roleMenuIdSet, menuIdSet);
                permissionApi.assignRoleMenu(role.getRoleId(), roleMenuIdSet);
                log.info("[updateTenantRoleMenu][角色({}/{})的权限修改为({})]", role.getTenantId(), role.getRoleId(), roleMenuIdSet);
            });
        });
    }

    @Override
    public TenantDO getTenant(Long tenantId) {
        return getById(tenantId);
    }

    @Override
    public PageResult<TenantDO> getTenantPage(TenantPageReq pageReq) {
        return tenantMapper.selectPage(pageReq);
    }

    @Override
    public TenantDO getTenantByName(String tenantName) {
        return tenantMapper.getTenantByName(tenantName);
    }

    @Override
    public TenantDO getTenantByWebsite(String website) {
        return tenantMapper.getTenantByWebsite(website);
    }

    @Override
    public Long getTenantCountByPackageId(Long packageId) {
        return tenantMapper.selectCountByPackageId(packageId);
    }

    @Override
    public List<TenantDO> listTenantByPackageId(Long packageId) {
        return tenantMapper.selectListByPackageId(packageId);
    }

    @Override
    public List<TenantDO> listTenantByStatusFlag(Integer statusFlag) {
        return tenantMapper.selectListByStatusFlag(statusFlag);
    }

    @Override
    public void validTenant(Long tenantId) {
        TenantDO tenant = getTenant(tenantId);
        if (tenant == null) {
            throw new TenantException(TenantExceptionEnum.TENANT_NOT_EXISTS);
        }
        if (StatusEnum.DISABLE.getCode().equals(tenant.getStatusFlag())) {
            throw new TenantException(TenantExceptionEnum.TENANT_DISABLE);
        }
        if (DateUtils.isExpired(tenant.getExpireTime())) {
            throw new TenantException(TenantExceptionEnum.TENANT_EXPIRED);
        }
    }

    // endregion

    // region 私有方法

    private void validTenantNameDuplicate(Long tenantId, String tenantName) {
        TenantDO tenant = tenantMapper.getTenantByName(tenantName);
        if (tenant == null) {
            return;
        }

        if (tenantId == null) {
            throw new TenantException(TenantExceptionEnum.TENANT_NAME_DUPLICATE);
        }
        if (ObjectUtil.notEqual(tenantId, tenant.getTenantId())) {
            throw new TenantException(TenantExceptionEnum.TENANT_NAME_DUPLICATE);
        }
    }

    private void validTenantWebsiteDuplicate(Long tenantId, String tenantWebsite) {
        if (StrUtil.isBlank(tenantWebsite)) {
            return;
        }
        TenantDO tenant = tenantMapper.getTenantByWebsite(tenantWebsite);
        if (tenant == null) {
            return;
        }

        if (tenantId == null) {
            throw new TenantException(TenantExceptionEnum.TENANT_WEBSITE_DUPLICATE);
        }
        if (ObjectUtil.notEqual(tenantId, tenant.getTenantId())) {
            throw new TenantException(TenantExceptionEnum.TENANT_WEBSITE_DUPLICATE);
        }
    }

    /**
     * 校验是否允许修改
     *
     * @param tenantId 租户ID
     * @return 租户信息
     */
    private TenantDO validateUpdateTenant(Long tenantId) {
        TenantDO tenant = getById(tenantId);
        if (tenant == null) {
            throw new TenantException(TenantExceptionEnum.TENANT_NOT_EXISTS);
        }
        if (isSystemTenant(tenant)) {
            throw new TenantException(TenantExceptionEnum.TENANT_CAN_NOT_UPDATE_SYSTEM);
        }
        return tenant;
    }

    /**
     * 是否为系统租户
     *
     * @return true=系统租户；false=非系统租户
     */
    private boolean isSystemTenant(TenantDO tenant) {
        return Objects.equals(tenant.getTenantId(), TenantDO.PACKAGE_ID_SYSTEM);
    }

    /**
     * 创建租户管理员角色并分配菜单权限
     *
     * @return 角色ID
     */
    private Long createRole(TenantPackageDO tenantPackage) {
        // 创建租户管理员角色
        RoleSaveReqDTO roleReq = RoleSaveReqDTO.builder()
                .roleName(RoleCodeEnum.TENANT_ADMIN.getName())
                .roleCode(RoleCodeEnum.TENANT_ADMIN.getCode())
                .statusFlag(StatusEnum.ENABLE.getCode())
                .roleSort(SysConstants.DEFAULT_ROLE_SORT)
                .remark(SysConstants.SYS_CREATE_ROLE_REMAKE)
                .build();
        Long roleId = roleServiceApi.createRole(roleReq, RoleTypeEnum.SYSTEM.getCode());
        // 分配权限
        permissionApi.assignRoleMenu(roleId, tenantPackage.getPackageMenuIds());
        return roleId;
    }

    /**
     * 创建租户管理员用户
     *
     * @param roleId  角色ID
     * @param saveReq 创建租户套餐参数
     * @return 用户ID
     */
    private Long createUser(Long roleId, TenantSaveReq saveReq) {
        Long userId = userServiceApi.createUser(TenantConvert.convertToCreateUserDTO(saveReq));
        permissionApi.assignUserRole(userId, CollUtil.set(false, roleId));
        return userId;
    }

    private void baseRemoveTenant(Set<Long> tenantIdSet) {
        Map<String, RemoveTenantCallbackApi> map = SpringUtil.getBeansOfType(RemoveTenantCallbackApi.class);
        for (RemoveTenantCallbackApi removeTenantCallbackApi : map.values()) {
            removeTenantCallbackApi.validateHaveTenantBind(tenantIdSet);
        }

        removeBatchByIds(tenantIdSet);

        for (RemoveTenantCallbackApi removeTenantCallbackApi : map.values()) {
            removeTenantCallbackApi.removeTenantAction(tenantIdSet);
        }
    }

    // endregion

}
