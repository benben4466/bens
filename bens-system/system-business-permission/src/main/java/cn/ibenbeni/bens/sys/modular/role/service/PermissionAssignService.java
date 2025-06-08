package cn.ibenbeni.bens.sys.modular.role.service;

import java.util.Set;

/**
 * 角色权限绑定业务
 *
 * @author: benben
 * @time: 2025/6/8 下午5:05
 */
public interface PermissionAssignService {

    /**
     * 获取角色绑定的菜单和菜单功能，直接返回菜单和菜单功能组成的ID集合
     *
     * @param roleId 角色ID
     */
    Set<Long> getRoleBindMenusAndOptions(Long roleId);

}
