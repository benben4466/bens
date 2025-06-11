package cn.ibenbeni.bens.sys.modular.role.service;

import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindPermissionRequest;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindPermissionResponse;

import java.util.Set;

/**
 * 角色权限绑定业务
 *
 * @author: benben
 * @time: 2025/6/8 下午5:05
 */
public interface PermissionAssignService {

    /**
     * 获取角色绑定的权限列表
     * <p>角色绑定的权限列表返回的是一个树形结构：第2层是应用下的菜单，第2层是菜单下的菜单功能</p>
     */
    RoleBindPermissionResponse getRoleBindPermission(RoleBindPermissionRequest roleBindPermissionRequest);

    /**
     * 构建一个权限树，包含了空的选择状态
     * <p>包含所有的菜单和菜单功能的结构</p>
     */
    RoleBindPermissionResponse createSelectTreeStructure();

    /**
     * 构建一个权限树，包含了空的选择状态
     * <p>包含指定范围内的菜单和菜单功能的结构</p>
     */
    RoleBindPermissionResponse createSelectTreeStructure(Set<Long> limitMenuIdsAndOptionIds);

    /**
     * 获取角色绑定的菜单和菜单功能，直接返回菜单和菜单功能组成的ID集合
     *
     * @param roleId 角色ID
     */
    Set<Long> getRoleBindMenusAndOptions(Long roleId);

    /**
     * 更新角色绑定菜单和菜单下的功能
     */
    void updateRoleBindPermission(RoleBindPermissionRequest roleBindPermissionRequest);

}
