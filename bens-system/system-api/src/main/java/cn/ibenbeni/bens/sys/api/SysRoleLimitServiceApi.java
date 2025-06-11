package cn.ibenbeni.bens.sys.api;

import java.util.List;
import java.util.Set;

/**
 * 获取角色的权限列表
 *
 * @author: benben
 * @time: 2025/6/9 下午10:22
 */
public interface SysRoleLimitServiceApi {

    /**
     * 获取角色的限制列表
     * <p>包含菜单ID和菜单功能ID</p>
     */
    Set<Long> getRoleBindLimitList(Long roleId);

    /**
     * 获取角色的限制列表
     * <p>包含菜单ID和菜单功能ID</p>
     */
    Set<Long> getRoleBindLimitList(List<Long> roleIdList);

}
