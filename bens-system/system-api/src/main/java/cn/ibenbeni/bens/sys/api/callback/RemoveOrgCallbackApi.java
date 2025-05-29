package cn.ibenbeni.bens.sys.api.callback;

import java.util.Set;

/**
 * 删除组织机构的回调方法
 *
 * @author benben
 */
public interface RemoveOrgCallbackApi {

    /**
     * 校验被删除的组织机构ID集合，是否有业务关联关系
     * <p>
     * 如果有绑定关系直接抛出异常即可
     *
     * @param beRemovedUserIdList 被删除的用户id集合
     */
    void validateHaveOrgBind(Set<Long> beRemovedUserIdList);

    /**
     * 删除组织机构的回调方法
     *
     * @param beRemovedUserIdList 被删除的用户id集合
     */
    void removeOrgAction(Set<Long> beRemovedUserIdList);

}
