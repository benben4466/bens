package cn.ibenbeni.bens.sys.modular.permission.service;

import cn.hutool.core.collection.CollUtil;

import java.util.Set;

/**
 * 权限-服务接口
 *
 * @author: benben
 * @time: 2025/6/8 下午5:05
 */
public interface PermissionService {

    // region 用户-角色相关方法

    /**
     * 用户分配角色
     *
     * @param userId    用户ID
     * @param roleIdSet 被分配的角色ID集合
     */
    void assignUserRole(Long userId, Set<Long> roleIdSet);

    // endregion


    // region 角色-菜单相关方法

    /**
     * 角色分配菜单
     *
     * @param roleId    角色ID
     * @param menuIdSet 被分配的菜单ID集合
     */
    void assignRoleMenu(Long roleId, Set<Long> menuIdSet);

    /**
     * 获取指定角色的所拥有菜单ID集合
     *
     * @param roleId 指定角色ID
     * @return 所拥有菜单ID集合
     */
    default Set<Long> getRoleMenuListByRoleId(Long roleId) {
        return getRoleMenuListByRoleId(CollUtil.set(false, roleId));
    }

    /**
     * 获取指定角色集合的所拥有菜单ID集合
     *
     * @param roleIdSet 指定角色ID集合
     * @return 所拥有菜单ID集合
     */
    Set<Long> getRoleMenuListByRoleId(Set<Long> roleIdSet);

    /**
     * 获取指定菜单绑定的所有角色ID集合
     *
     * @param menuId 菜单ID
     * @return 角色ID集合
     */
    Set<Long> getMenuRoleIdListByMenuId(Long menuId);

    // endregion

}
