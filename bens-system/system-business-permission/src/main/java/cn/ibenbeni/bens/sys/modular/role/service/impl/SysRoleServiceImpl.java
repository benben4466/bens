package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.enums.permission.DataScopeTypeEnum;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.sys.api.callback.RemoveRoleCallbackApi;
import cn.ibenbeni.bens.sys.api.enums.role.RoleCodeEnum;
import cn.ibenbeni.bens.sys.api.enums.role.RoleTypeEnum;
import cn.ibenbeni.bens.sys.api.exception.SysException;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleDO;
import cn.ibenbeni.bens.sys.modular.role.enums.exception.SysRoleExceptionEnum;
import cn.ibenbeni.bens.sys.modular.role.mapper.SysRoleMapper;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RolePageReq;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleSaveReq;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统角色-业务实现层
 *
 * @author benben
 * @date 2025/5/3  下午10:49
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDO> implements SysRoleService {

    // region 属性

    @Resource
    private SysRoleMapper sysRoleMapper;

    // endregion


    // region 公共方法

    @Override
    public Long createRole(RoleSaveReq req, Integer roleType) {
        // 校验角色参数
        validateRoleDuplicate(null, req.getRoleName(), req.getRoleCode());

        SysRoleDO role = BeanUtil.toBean(req, SysRoleDO.class);
        role.setRoleType(ObjectUtil.defaultIfNull(roleType, RoleTypeEnum.CUSTOM.getCode()));
        role.setStatusFlag(ObjectUtil.defaultIfNull(req.getStatusFlag(), StatusEnum.ENABLE.getCode()));
        role.setDataScopeType(DataScopeTypeEnum.SELF.getCode());
        save(role);
        return role.getRoleId();
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(Long roleId) {
        // 校验是否可以删除
        validateRoleForUpdate(roleId);

        // 删除
        baseDelete(CollUtil.set(false, roleId));
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(Set<Long> roleIdSet) {
        // 校验是否可以删除
        roleIdSet.forEach(this::validateRoleForUpdate);

        baseDelete(roleIdSet);
    }

    @Override
    public void updateRole(RoleSaveReq req) {
        // 校验是否可以更新
        validateRoleForUpdate(req.getRoleId());
        // 校验角色参数唯一性
        validateRoleDuplicate(req.getRoleId(), req.getRoleName(), req.getRoleCode());

        SysRoleDO role = getById(req.getRoleId());
        BeanUtil.copyProperties(req, role);
        updateById(role);
    }

    @Override
    public void updateRoleDataScope(Long roleId, Integer dataScopeType, Set<Long> dataScopeDeptIds) {
        // 校验是否可以更新
        validateRoleForUpdate(roleId);

        // 填充值
        SysRoleDO updateObj = new SysRoleDO();
        updateObj.setRoleId(roleId);
        updateObj.setDataScopeType(dataScopeType);
        updateObj.setDataScopeDeptIds(dataScopeDeptIds);

        updateById(updateObj);
    }

    @Override
    public SysRoleDO getRole(Long roleId) {
        return getById(roleId);
    }

    @Override
    public List<SysRoleDO> getRoleList(Set<Long> roleIdSet) {
        if (CollUtil.isEmpty(roleIdSet)) {
            return new ArrayList<>();
        }
        return listByIds(roleIdSet);
    }

    @Override
    public List<SysRoleDO> getRoleListByStatus(Set<Integer> statusSet) {
        if (CollUtil.isEmpty(statusSet)) {
            return new ArrayList<>();
        }
        return sysRoleMapper.selectListByStatus(statusSet);
    }

    @Override
    public List<SysRoleDO> getRoleList() {
        return list();
    }

    @Override
    public PageResult<SysRoleDO> getRolePage(RolePageReq pageReq) {
        return sysRoleMapper.selectPage(pageReq);
    }

    @Override
    public boolean hasAnySuperAdmin(Set<Long> roleIdSet) {
        if (CollUtil.isEmpty(roleIdSet)) {
            return false;
        }
        return roleIdSet.stream().anyMatch(roleId -> {
            SysRoleDO role = getRole(roleId);
            return role != null && RoleCodeEnum.isSuperAdmin(role.getRoleCode());
        });
    }

    @Override
    public void validateRole(Set<Long> roleIdSet) {
        if (CollUtil.isEmpty(roleIdSet)) {
            return;
        }
        List<SysRoleDO> roleList = listByIds(roleIdSet);
        Map<Long, SysRoleDO> roleMap = CollectionUtils.convertMap(roleList, SysRoleDO::getRoleId);
        roleIdSet.forEach(roleId -> {
            SysRoleDO role = roleMap.get(roleId);
            if (role == null) {
                throw new SysException(SysRoleExceptionEnum.SYS_ROLE_NOT_EXISTED);
            }
            if (StatusEnum.DISABLE.getCode().equals(role.getStatusFlag())) {
                throw new SysException(SysRoleExceptionEnum.ROLE_DISABLED, role.getRoleName());
            }
        });
    }

    @Override
    public void validateHaveTenantBind(Set<Long> beRemovedIdSet) {
    }

    @Override
    public void removeTenantAction(Set<Long> beRemovedPackageIdSet) {
        // 删除该租户下所有角色
        Set<Long> roleIdSet = CollectionUtils.convertSet(list(), SysRoleDO::getRoleId);
        baseDelete(roleIdSet);
    }

    // endregion

    // region 私有方法

    /**
     * 校验角色的唯一字段是否重复
     *
     * <p>1.是否存在相同名字的角色；2.是否存在相同编码的角色</p>
     *
     * @param roleId   角色ID
     * @param roleName 角色名称
     * @param roleCode 角色编码
     */
    private void validateRoleDuplicate(Long roleId, String roleName, String roleCode) {
        // 不允许创建超级管理员
        if (RoleCodeEnum.isSuperAdmin(roleName)) {
            throw new SysException(SysRoleExceptionEnum.ROLE_ADMIN_CODE_ERROR);
        }

        // 校验名称已使用
        SysRoleDO role = sysRoleMapper.selectByName(roleName);
        if (role != null && ObjectUtil.notEqual(role.getRoleId(), roleId)) {
            throw new SysException(SysRoleExceptionEnum.ROLE_NAME_DUPLICATE, roleName);
        }

        // 校验角色编码已使用
        if (StrUtil.isBlank(roleCode)) {
            throw new SysException(SysRoleExceptionEnum.ROLE_CODE_LACK);
        }
        role = sysRoleMapper.selectByCode(roleCode);
        if (role != null && ObjectUtil.notEqual(role.getRoleId(), roleId)) {
            throw new SysException(SysRoleExceptionEnum.ROLE_CODE_DUPLICATE, roleCode);
        }
    }

    /**
     * 校验角色是否可以被更新
     */
    void validateRoleForUpdate(Long roleId) {
        SysRoleDO role = getById(roleId);
        if (role == null) {
            throw new SysException(SysRoleExceptionEnum.SYS_ROLE_NOT_EXISTED);
        }
        // 内置角色不允许修改/删除
        if (RoleTypeEnum.SYSTEM.getCode().equals(role.getRoleType())) {
            throw new SysException(SysRoleExceptionEnum.ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
        }
    }

    /**
     * 删除角色的基础业务操作
     *
     * @param roleIdList 角色ID集合
     */
    private void baseDelete(Set<Long> roleIdList) {
        Map<String, RemoveRoleCallbackApi> callbackApiMap = SpringUtil.getBeansOfType(RemoveRoleCallbackApi.class);
        // 执行角色相关的校验
        for (RemoveRoleCallbackApi removeRoleCallbackApi : callbackApiMap.values()) {
            removeRoleCallbackApi.validateHaveRoleBind(roleIdList);
        }

        removeBatchByIds(roleIdList);

        // 执行角色相关关联业务的删除操作
        for (RemoveRoleCallbackApi removeRoleCallbackApi : callbackApiMap.values()) {
            removeRoleCallbackApi.removeRoleAction(roleIdList);
        }
    }

    // endregion

}
