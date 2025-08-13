package cn.ibenbeni.bens.sys.api;

import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.auth.api.exception.AuthException;
import cn.ibenbeni.bens.sys.api.pojo.permission.dto.DeptDataPermissionRespDTO;

import java.util.List;
import java.util.Set;

/**
 * 权限分配业务API
 *
 * @author: benben
 * @time: 2025/7/2 上午9:41
 */
public interface PermissionApi {

    /**
     * 校验当前用户是否有某个接口的权限
     *
     * @param token              用户登录Token
     * @param permissionCodeList 需要的权限编码
     * @throws AuthException 认证异常
     */
    void validatePermission(String token, List<String> permissionCodeList) throws AuthException;

    /**
     * 获取用户的权限编码集合
     *
     * @param userId 用户ID
     * @return 权限编码集合
     */
    Set<String> getUserPermissionCodeList(Long userId);

    /**
     * 获取登陆用户的部门数据权限
     *
     * @param userId 用户ID
     * @return 部门数据权限
     */
    DeptDataPermissionRespDTO getDeptDataPermission(Long userId);

    // region 角色-菜单相关方法

    /**
     * 角色分配菜单
     *
     * @param roleId    角色ID
     * @param menuIdSet 被分配的菜单ID集合
     */
    void assignRoleMenu(Long roleId, Set<Long> menuIdSet);

    /**
     * 获取角色的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    default Set<Long> listRoleMenuByRoleId(Long roleId) {
        return listRoleMenuByRoleId(CollUtil.set(false, roleId));
    }

    /**
     * 获取角色的菜单ID集合
     *
     * @param roleIdSet 角色ID集合
     * @return 菜单ID集合
     */
    Set<Long> listRoleMenuByRoleId(Set<Long> roleIdSet);
    // endregion

    // region 用户-角色相关方法

    /**
     * 用户分配角色
     *
     * @param userId    用户ID
     * @param roleIdSet 被分配的角色ID集合
     */
    void assignUserRole(Long userId, Set<Long> roleIdSet);

    // endregion

}
