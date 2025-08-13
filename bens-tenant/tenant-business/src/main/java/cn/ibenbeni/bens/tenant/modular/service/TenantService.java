package cn.ibenbeni.bens.tenant.modular.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.tenant.modular.entity.TenantDO;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantPageReq;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantSaveReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 租户服务类
 *
 * @author: benben
 * @time: 2025/7/1 上午11:05
 */
public interface TenantService extends IService<TenantDO> {

    /**
     * 创建租户
     *
     * @return 租户ID
     */
    Long createTenant(TenantSaveReq saveReq);

    /**
     * 删除租户
     *
     * @param tenantId 租户ID
     */
    void deleteTenant(Long tenantId);

    /**
     * 批量删除租户
     *
     * @param tenantIdSet 租户ID集合
     */
    void deleteTenant(Set<Long> tenantIdSet);

    /**
     * 更新租户
     */
    void updateTenant(TenantSaveReq updateReq);

    /**
     * 更新租户的角色菜单
     *
     * @param tenantId  租户ID
     * @param menuIdSet 菜单ID集合
     */
    void updateTenantMenu(Long tenantId, Set<Long> menuIdSet);

    /**
     * 获取租户信息
     *
     * @param tenantId 租户ID
     * @return 租户信息
     */
    TenantDO getTenant(Long tenantId);

    /**
     * 获取租户分页列表
     */
    PageResult<TenantDO> getTenantPage(TenantPageReq pageReq);

    /**
     * 根据租户名称,获取租户信息
     *
     * @param tenantName 租户名称
     */
    TenantDO getTenantByName(String tenantName);

    /**
     * 根据租户域名,获取租户信息
     *
     * @param website 租户域名
     */
    TenantDO getTenantByWebsite(String website);

    /**
     * 根据租户套餐ID,获取租户数量
     *
     * @param packageId 租户套餐ID
     * @return 租户数量
     */
    Long getTenantCountByPackageId(Long packageId);

    /**
     * 根据租户套餐ID,获取租户列表
     *
     * @param packageId 租户套餐ID
     * @return 租户列表
     */
    List<TenantDO> listTenantByPackageId(Long packageId);

    /**
     * 根据租户状态,获取租户列表
     *
     * @param statusFlag 租户状态
     */
    List<TenantDO> listTenantByStatusFlag(Integer statusFlag);

    /**
     * 校验租户信息
     *
     * @param tenantId 租户ID
     */
    void validTenant(Long tenantId);

}
