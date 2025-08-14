package cn.ibenbeni.bens.tenant.api.callback;

import java.util.Set;

/**
 * 删除租户回调接口
 */
public interface RemoveTenantCallbackApi {

    /**
     * 校验指定租户ID集合是否和业务的绑定关系
     * <p>如果有绑定关系直接抛出异常即可</p>
     *
     * @param beRemovedIdSet 被删除的租户ID集合
     */
    void validateHaveTenantBind(Set<Long> beRemovedIdSet);

    /**
     * 删除租户的回调，删除租户后自动触发
     *
     * @param beRemovedPackageIdSet 被删除的租户ID集合
     */
    void removeTenantAction(Set<Long> beRemovedPackageIdSet);

}
