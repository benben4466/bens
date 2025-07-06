package cn.ibenbeni.bens.sys.api;

import java.util.Set;

/**
 * 权限分配业务API
 *
 * @author: benben
 * @time: 2025/7/2 上午9:41
 */
public interface PermissionAssignApi {

    /**
     * 角色绑定菜单和菜单功能权限
     */
    void assignRoleMenuAndOption(Long roleId, Set<Long> menuSet, Set<Long> menuOptionSet);

}
