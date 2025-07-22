package cn.ibenbeni.bens.tenant.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.db.api.factory.PageFactory;
import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.tenant.api.callback.RemoveTenantPackageCallbackApi;
import cn.ibenbeni.bens.tenant.api.exception.TenantException;
import cn.ibenbeni.bens.tenant.api.exception.enums.TenantExceptionEnum;
import cn.ibenbeni.bens.tenant.modular.entity.SysTenantPackage;
import cn.ibenbeni.bens.tenant.modular.mapper.SysTenantPackageMapper;
import cn.ibenbeni.bens.tenant.modular.pojo.request.SysTenantPackageRequest;
import cn.ibenbeni.bens.tenant.modular.service.SysTenantPackageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 租户套餐服务实现类
 *
 * @author: benben
 * @time: 2025/6/30 下午5:28
 */
@Service
public class SysTenantPackageServiceImpl extends ServiceImpl<SysTenantPackageMapper, SysTenantPackage> implements SysTenantPackageService {

    @Override
    public void add(SysTenantPackageRequest sysTenantPackageRequest) {
        // 校验租户套餐名称唯一
        this.validateTenantPackageNameUnique(null, sysTenantPackageRequest.getPackageName());

        SysTenantPackage tenantPackage = BeanUtil.toBean(sysTenantPackageRequest, SysTenantPackage.class);
        // 填充菜单及菜单功能
        // TenantPermissionFactory.fillPermissionMenus(tenantPackage, sysTenantPackageRequest.getPermissionList());

        this.save(tenantPackage);

        // TODO 记录日志
    }

    @Override
    public void del(SysTenantPackageRequest sysTenantPackageRequest) {
        SysTenantPackage dbTenantPackage = this.queryTenantPackage(sysTenantPackageRequest.getPackageId());

        // 删除
        this.baseDelete(CollectionUtil.set(false, dbTenantPackage.getPackageId()));

        // TODO 记录日志
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDelete(SysTenantPackageRequest sysTenantPackageRequest) {
        this.baseDelete(sysTenantPackageRequest.getPackageIdList());
        // TODO 记录日志
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysTenantPackageRequest sysTenantPackageRequest) {
        SysTenantPackage tenantPackage = this.queryTenantPackage(sysTenantPackageRequest.getPackageId());

        // 校验租户套餐名称唯一
        validateTenantPackageNameUnique(sysTenantPackageRequest.getPackageId(), sysTenantPackageRequest.getPackageName());

        // 填充菜单及菜单功能
//        Set<Long> tenantPackageMenuIds = TenantPermissionFactory.getTenantPackageMenuIds(sysTenantPackageRequest.getPermissionList());
//        Set<Long> tenantPackageMenuOptionIds = TenantPermissionFactory.getTenantPackageMenuOptionIds(sysTenantPackageRequest.getPermissionList());
//        if (!CollUtil.isEqualList(tenantPackageMenuIds, tenantPackage.getPackageMenuIds())) {
//            // TODO 修改所属租户的菜单权限
//        }
//        if (!CollUtil.isEqualList(tenantPackageMenuOptionIds, tenantPackage.getPackageMenuOptionIds())) {
//            // TODO 修改所属租户的菜单功能权限
//        }

        // 更新租户套餐
        BeanUtil.copyProperties(sysTenantPackageRequest, tenantPackage);
//        tenantPackage.setPackageMenuIds(tenantPackageMenuIds);
//        tenantPackage.setPackageMenuOptionIds(tenantPackageMenuOptionIds);

        this.updateById(tenantPackage);

        // TODO 记录日志
    }

    @Override
    public SysTenantPackage detail(SysTenantPackageRequest sysTenantPackageRequest) {
        return this.getById(sysTenantPackageRequest.getPackageId());
    }

    @Override
    public List<SysTenantPackage> findList(SysTenantPackageRequest sysTenantPackageRequest) {
        return this.list(this.createWrapper(sysTenantPackageRequest));
    }

    @Override
    public PageResult<SysTenantPackage> findPage(SysTenantPackageRequest sysTenantPackageRequest) {
        LambdaQueryWrapper<SysTenantPackage> queryWrapper = this.createWrapper(sysTenantPackageRequest);
        Page<SysTenantPackage> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public SysTenantPackage validTenantPackage(Long packageId) {
        SysTenantPackage tenantPackage = this.getById(packageId);
        if (tenantPackage == null) {
            throw new TenantException(TenantExceptionEnum.TENANT_PACKAGE_NOT_EXIST);
        }

        if (StatusEnum.DISABLE.getCode().equals(tenantPackage.getStatusFlag())) {
            throw new TenantException(TenantExceptionEnum.TENANT_PACKAGE_DISABLE);
        }
        return tenantPackage;
    }

    /**
     * 检验租户套餐名称是否唯一
     *
     * @param packageId   租户套餐ID；可为空
     * @param packageName 租户套餐名称；必填
     */
    void validateTenantPackageNameUnique(Long packageId, String packageName) {
        if (StrUtil.isBlank(packageName)) {
            throw new TenantException(TenantExceptionEnum.TENANT_PACKAGE_PARAM_ERROR, "租户套餐名称为空");
        }
        packageName = packageName.trim();

        LambdaQueryWrapper<SysTenantPackage> queryWrapper = Wrappers.lambdaQuery(SysTenantPackage.class)
                .eq(SysTenantPackage::getPackageName, packageName)
                .ne(ObjectUtil.isNotNull(packageId), SysTenantPackage::getPackageId, packageId);
        List<SysTenantPackage> list = this.list(queryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            throw new TenantException(TenantExceptionEnum.TENANT_PACKAGE_EXISTED);
        }
    }

    private SysTenantPackage queryTenantPackage(Long packageId) {
        SysTenantPackage tenantPackage = this.getById(packageId);
        if (tenantPackage == null) {
            throw new TenantException(TenantExceptionEnum.TENANT_PACKAGE_NOT_EXIST);
        }
        return tenantPackage;
    }

    private void baseDelete(Set<Long> idSet) {
        Map<String, RemoveTenantPackageCallbackApi> map = SpringUtil.getBeansOfType(RemoveTenantPackageCallbackApi.class);
        // 验证绑定关系
        for (RemoveTenantPackageCallbackApi callbackApi : map.values()) {
            callbackApi.validateHaveTenantPackageBind(idSet);
        }

        this.removeBatchByIds(idSet);

        // 删除回调
        for (RemoveTenantPackageCallbackApi callbackApi : map.values()) {
            callbackApi.removeTenantPackageAction(idSet);
        }
    }

    private LambdaQueryWrapper<SysTenantPackage> createWrapper(SysTenantPackageRequest sysTenantPackageRequest) {
        return Wrappers.lambdaQuery(SysTenantPackage.class)
                .orderByAsc(SysTenantPackage::getCreateTime);
    }


}
