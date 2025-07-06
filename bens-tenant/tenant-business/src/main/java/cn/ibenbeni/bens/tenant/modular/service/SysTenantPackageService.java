package cn.ibenbeni.bens.tenant.modular.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.tenant.modular.entity.SysTenantPackage;
import cn.ibenbeni.bens.tenant.modular.pojo.request.SysTenantPackageRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 租户套餐服务类
 *
 * @author: benben
 * @time: 2025/6/30 下午5:27
 */
public interface SysTenantPackageService extends IService<SysTenantPackage> {

    /**
     * 新增
     */
    void add(SysTenantPackageRequest sysTenantPackageRequest);

    /**
     * 删除
     */
    void del(SysTenantPackageRequest sysTenantPackageRequest);

    /**
     * 批量删除
     */
    void batchDelete(SysTenantPackageRequest sysTenantPackageRequest);

    /**
     * 编辑
     */
    void edit(SysTenantPackageRequest sysTenantPackageRequest);

    /**
     * 查询详情
     */
    SysTenantPackage detail(SysTenantPackageRequest sysTenantPackageRequest);

    /**
     * 查询列表
     */
    List<SysTenantPackage> findList(SysTenantPackageRequest sysTenantPackageRequest);

    /**
     * 查询列表（分页）
     */
    PageResult<SysTenantPackage> findPage(SysTenantPackageRequest sysTenantPackageRequest);

    /**
     * 校验租户套餐
     * <p>校验是否为空、是否禁用</p>
     *
     * @param packageId 租户套餐ID
     * @return 租户套餐信息
     */
    SysTenantPackage validTenantPackage(Long packageId);

}
