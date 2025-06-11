package cn.ibenbeni.bens.sys.api;

import java.util.List;
import java.util.Set;

/**
 * 用户角色关联关系的Api
 *
 * @author: benben
 * @time: 2025/6/2 下午2:22
 */
public interface SysUserRoleServiceApi {

    /**
     * 获取用户所有的角色ID列表
     */
    List<Long> getUserRoleIdList(Long userId);

    /**
     * 获取用户系统级别角色
     * <p>用在用户管理界面，分配角色时使用</p>
     */
    Set<Long> getUserSystemRoleIdList(Long userId);

    /**
     * 根据角色ID查询该角色下用户ID列表
     */
    List<Long> findUserIdsByRoleId(Long roleId);

    /**
     * 获取用户的角色范围列表，角色范围指用户的角色可分配的权限列表
     *
     * @param userId 用户ID
     */
    Set<Long> findUserRoleLimitScope(Long userId);

    /**
     * 获取当前登录用户的角色范围列表，角色范围指用户的角色可分配的权限列表
     */
    Set<Long> findCurrentUserRoleLimitScope();

}
