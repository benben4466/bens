package cn.ibenbeni.bens.tenant.api.callback;

import java.util.Set;

/**
 * 删除租户套餐的回调方法
 *
 * @author: benben
 * @time: 2025/6/30 下午10:43
 */
public interface RemoveTenantPackageCallbackApi {

    /**
     * 校验指定租户套餐ID集合是否和业务的绑定关系
     * <p>如果有绑定关系直接抛出异常即可</p>
     *
     * @param beRemovedPackageIdList 被删除的租户套餐ID集合
     */
    void validateHaveTenantPackageBind(Set<Long> beRemovedPackageIdList);

    /**
     * 删除租户套餐的回调，删除租户套餐后自动触发
     *
     * @param beRemovedPackageIdList 被删除的租户套餐ID集合
     */
    void removeTenantPackageAction(Set<Long> beRemovedPackageIdList);

}
