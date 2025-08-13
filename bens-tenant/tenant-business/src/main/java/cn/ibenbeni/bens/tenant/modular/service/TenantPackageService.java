package cn.ibenbeni.bens.tenant.modular.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.tenant.modular.entity.TenantPackageDO;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantPackagePageReq;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantPackageSaveReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 租户套餐服务类
 *
 * @author: benben
 * @time: 2025/6/30 下午5:27
 */
public interface TenantPackageService extends IService<TenantPackageDO> {

    /**
     * 创建租户套餐
     *
     * @return 租户套餐ID
     */
    Long createTenantPackage(TenantPackageSaveReq saveReq);

    /**
     * 删除租户套餐
     *
     * @param packageId 租户套餐ID
     */
    void deleteTenantPackage(Long packageId);

    /**
     * 批量删除租户套餐
     *
     * @param packageIdSet 租户套餐ID集合
     */
    void deleteTenantPackage(Set<Long> packageIdSet);

    /**
     * 更新租户套餐
     */
    void updateTenantPackage(TenantPackageSaveReq updateReq);

    /**
     * 获得租户套餐
     *
     * @param packageId 租户套餐ID
     * @return 租户套餐信息
     */
    TenantPackageDO getTenantPackage(Long packageId);

    /**
     * 获得租户套餐列表
     *
     * @param statusFlag 租户套餐状态
     */
    List<TenantPackageDO> listTenantPackageByStatusFlag(Integer statusFlag);

    /**
     * 获得租户套餐分页列表
     */
    PageResult<TenantPackageDO> getTenantPackagePage(TenantPackagePageReq pageReq);

    /**
     * 校验租户套餐
     * <p>校验是否为空、是否禁用</p>
     *
     * @param packageId 租户套餐ID
     * @return 租户套餐信息
     */
    TenantPackageDO validTenantPackage(Long packageId);

}
