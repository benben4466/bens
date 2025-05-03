package cn.ibenbeni.bens.sys.api.callback;

import java.util.Set;

/**
 * 删除用户的回调方法
 *
 * @author benben
 * @date 2025/5/3  下午7:17
 */
public interface RemoveUserCallbackApi {

    /**
     * 校验被删除的用户id集合，是否有业务关联关系
     * <p>
     * 如果有绑定关系直接抛出异常即可
     *
     * @param beRemovedUserIdList 被删除的用户id集合
     */
    void validateHaveUserBind(Set<Long> beRemovedUserIdList);

    /**
     * 删除用户信息的回调方法
     *
     * @param beRemovedUserIdList 被删除的用户id集合
     */
    void removeUserAction(Set<Long> beRemovedUserIdList);

}
