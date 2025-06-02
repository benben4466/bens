package cn.ibenbeni.bens.sys.api.callback;

import java.util.Set;

/**
 * 删除组织机构的回调方法
 *
 * @author benben
 */
public interface RemoveOrgCallbackApi {

    /**
     * 校验指定orgId集合是否有和业务的绑定关系
     * <p>如果有绑定关系直接抛出异常即可</p>
     *
     * @param beRemovedOrgIdList 被删除的组织机构id集合
     */
    void validateHaveOrgBind(Set<Long> beRemovedOrgIdList);

    /**
     * 删除组织机构的回调，删除组织机构后自动触发
     *
     * @param beRemovedOrgIdList 被删除的组织机构id集合
     */
    void removeOrgAction(Set<Long> beRemovedOrgIdList);

}
