package cn.ibenbeni.bens.tenant.modular.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.tenant.api.exception.TenantException;
import cn.ibenbeni.bens.tenant.api.exception.enums.TenantExceptionEnum;
import cn.ibenbeni.bens.tenant.modular.entity.TenantDO;
import cn.ibenbeni.bens.tenant.modular.entity.TenantPackageDO;
import cn.ibenbeni.bens.tenant.modular.mapper.TenantPackageMapper;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantPackagePageReq;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantPackageSaveReq;
import cn.ibenbeni.bens.tenant.modular.service.TenantPackageService;
import cn.ibenbeni.bens.tenant.modular.service.TenantService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 租户套餐服务实现类
 *
 * @author: benben
 * @time: 2025/6/30 下午5:28
 */
@Service
public class TenantPackageServiceImpl extends ServiceImpl<TenantPackageMapper, TenantPackageDO> implements TenantPackageService {

    // region 属性

    @Resource
    private TenantPackageMapper tenantPackageMapper;

    @Resource
    private TenantService tenantService;

    // endregion

    // region 公共方法

    @Override
    public Long createTenantPackage(TenantPackageSaveReq saveReq) {
        validateTenantPackageNameUnique(null, saveReq.getPackageName());

        TenantPackageDO tenantPackage = BeanUtil.toBean(saveReq, TenantPackageDO.class);
        tenantPackage.setPackageId(null); // 防止前端传入
        save(tenantPackage);
        return tenantPackage.getPackageId();
    }

    @Override
    public void deleteTenantPackage(Long packageId) {
        // 校验租户套餐是否存在
        validateTenantPackageExists(packageId);
        validateTenantUsed(packageId);

        removeById(packageId);
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteTenantPackage(Set<Long> packageIdSet) {
        // 校验租户套餐是否正在使用
        packageIdSet.forEach(this::validateTenantUsed);

        removeByIds(packageIdSet);
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void updateTenantPackage(TenantPackageSaveReq updateReq) {
        // 校验租户套餐是否存在
        TenantPackageDO tenantPackage = validateTenantPackageExists(updateReq.getPackageId());
        // 校验租户套餐名称是否唯一
        validateTenantPackageNameUnique(updateReq.getPackageId(), updateReq.getPackageName());
        Set<Long> oldMenuIds = tenantPackage.getPackageMenuIds();

        BeanUtil.copyProperties(updateReq, tenantPackage);
        updateById(tenantPackage);

        // 若租户套餐授权的菜单变化，则修改所属租户的菜单权限
        if (!CollUtil.isEqualList(oldMenuIds, updateReq.getPackageMenuIds())) {
            List<TenantDO> tenants = tenantService.listTenantByPackageId(tenantPackage.getPackageId());
            tenants.forEach(tenant -> tenantService.updateTenantMenu(tenant.getTenantId(), updateReq.getPackageMenuIds()));
        }
    }

    @Override
    public TenantPackageDO getTenantPackage(Long packageId) {
        return getById(packageId);
    }

    @Override
    public List<TenantPackageDO> listTenantPackageByStatusFlag(Integer statusFlag) {
        return tenantPackageMapper.selectListByStatusFlag(statusFlag);
    }

    @Override
    public PageResult<TenantPackageDO> getTenantPackagePage(TenantPackagePageReq pageReq) {
        return tenantPackageMapper.selectPage(pageReq);
    }

    @Override
    public TenantPackageDO validTenantPackage(Long packageId) {
        TenantPackageDO tenantPackage = this.getById(packageId);
        if (tenantPackage == null) {
            throw new TenantException(TenantExceptionEnum.TENANT_PACKAGE_NOT_EXIST);
        }

        if (StatusEnum.DISABLE.getCode().equals(tenantPackage.getStatusFlag())) {
            throw new TenantException(TenantExceptionEnum.TENANT_PACKAGE_DISABLE);
        }
        return tenantPackage;
    }

    // endregion

    // region 私有方法

    /**
     * 校验租户套餐名称是否唯一
     *
     * @param packageId   租户套餐ID
     * @param packageName 租户套餐名称
     */
    private void validateTenantPackageNameUnique(Long packageId, String packageName) {
        TenantPackageDO tenantPackage = tenantPackageMapper.selectByPackageName(packageName);
        if (tenantPackage == null) {
            return;
        }

        if (packageId == null) {
            throw new TenantException(TenantExceptionEnum.TENANT_PACKAGE_NAME_DUPLICATE);
        }
        if (ObjectUtil.notEqual(packageId, tenantPackage.getPackageId())) {
            throw new TenantException(TenantExceptionEnum.TENANT_PACKAGE_NAME_DUPLICATE);
        }
    }

    private TenantPackageDO validateTenantPackageExists(Long packageId) {
        TenantPackageDO tenantPackage = getById(packageId);
        if (tenantPackage == null) {
            throw new TenantException(TenantExceptionEnum.TENANT_PACKAGE_NOT_EXIST);
        }
        return tenantPackage;
    }

    /**
     * 校验租户套餐是否正在使用
     *
     * @param packageId 租户套餐ID
     */
    private void validateTenantUsed(Long packageId) {
        if (tenantService.getTenantCountByPackageId(packageId) > 0) {
            throw new TenantException(TenantExceptionEnum.TENANT_PACKAGE_USED);
        }
    }

    // endregion

}
