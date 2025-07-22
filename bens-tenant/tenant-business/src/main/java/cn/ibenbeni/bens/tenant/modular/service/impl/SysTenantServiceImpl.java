package cn.ibenbeni.bens.tenant.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.tenant.api.exception.TenantException;
import cn.ibenbeni.bens.tenant.api.exception.enums.TenantExceptionEnum;
import cn.ibenbeni.bens.tenant.modular.entity.SysTenant;
import cn.ibenbeni.bens.tenant.modular.entity.SysTenantPackage;
import cn.ibenbeni.bens.tenant.modular.mapper.SysTenantMapper;
import cn.ibenbeni.bens.tenant.modular.pojo.request.SysTenantRequest;
import cn.ibenbeni.bens.tenant.modular.service.SysTenantPackageService;
import cn.ibenbeni.bens.tenant.modular.service.SysTenantService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 租户服务实现类
 *
 * @author: benben
 * @time: 2025/7/1 上午11:06
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    @Lazy
    @Resource
    private SysTenantPackageService sysTenantPackageService;

    @Override
    public void add(SysTenantRequest sysTenantRequest) {
        // 校验租户名称唯一
        this.validateTenantNameUnique(null, sysTenantRequest.getTenantName());
        // 校验租户域名唯一
        this.validateTenantWebsiteUnique(null, sysTenantRequest.getTenantWebsite());
        // 校验租户套餐是否启用
        SysTenantPackage tenantPackage = sysTenantPackageService.validTenantPackage(sysTenantRequest.getTenantPackageId());

        // 创建租户
        SysTenant tenant = BeanUtil.toBean(sysTenantRequest, SysTenant.class);
        this.save(tenant);

        // 创建租户管理员角色
        Long roleId = this.createRole(tenantPackage);
        // 创建

        // TODO 记录日志
    }

    @Override
    public void del(SysTenantRequest sysTenantRequest) {
    }

    @Override
    public void batchDelete(SysTenantRequest sysTenantRequest) {
    }

    @Override
    public void edit(SysTenantRequest sysTenantRequest) {
    }

    @Override
    public SysTenant detail(SysTenantRequest sysTenantRequest) {
        return null;
    }

    @Override
    public SysTenant getByTenantName(String tenantName) {
        return null;
    }

    @Override
    public List<SysTenant> findList(SysTenantRequest sysTenantRequest) {
        return null;
    }

    @Override
    public PageResult<SysTenant> findPage(SysTenantRequest sysTenantRequest) {
        return null;
    }

    /**
     * 校验租户名称唯一
     *
     * @param tenantId   租户ID
     * @param tenantName 租户名称
     */
    private void validateTenantNameUnique(Long tenantId, String tenantName) {
        if (StrUtil.isBlank(tenantName)) {
            throw new TenantException(TenantExceptionEnum.TENANT_NAME_BLANK);
        }
        tenantName = tenantName.trim();

        LambdaQueryWrapper<SysTenant> queryWrapper = Wrappers.lambdaQuery(SysTenant.class)
                .eq(SysTenant::getTenantName, tenantName)
                .ne(ObjectUtil.isNotNull(tenantId), SysTenant::getTenantId, tenantId);
        List<SysTenant> list = this.list(queryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            throw new TenantException(TenantExceptionEnum.TENANT_EXISTED);
        }
    }

    /**
     * 校验租户域名唯一
     *
     * @param tenantId      租户ID
     * @param tenantWebsite 租户域名
     */
    private void validateTenantWebsiteUnique(Long tenantId, String tenantWebsite) {
        if (StrUtil.isBlank(tenantWebsite)) {
            throw new TenantException(TenantExceptionEnum.TENANT_WEBSITE_BLANK);
        }
        tenantWebsite = tenantWebsite.trim();

        LambdaQueryWrapper<SysTenant> queryWrapper = Wrappers.lambdaQuery(SysTenant.class)
                .eq(SysTenant::getTenantWebsite, tenantWebsite)
                .ne(ObjectUtil.isNotNull(tenantId), SysTenant::getTenantId, tenantId);
        List<SysTenant> list = this.list(queryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            throw new TenantException(TenantExceptionEnum.TENANT_WEBSITE_EXISTED);
        }
    }

    /**
     * 创建租户管理员角色
     *
     * @param tenantPackage 租户套餐实例
     * @return 租户管理员角色ID
     */
    private Long createRole(SysTenantPackage tenantPackage) {
        return null;
    }

    /**
     * 创建租户管理员用户
     *
     * @param roleId           角色ID
     * @param sysTenantRequest 创建租户参数
     * @return 租户管理员用户ID
     */
    private Long createUser(Long roleId, SysTenantRequest sysTenantRequest) {
        return null;
    }

}
