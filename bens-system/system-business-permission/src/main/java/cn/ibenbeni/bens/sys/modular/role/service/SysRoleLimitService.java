package cn.ibenbeni.bens.sys.modular.role.service;

import cn.ibenbeni.bens.sys.api.SysRoleLimitServiceApi;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleLimit;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindPermissionRequest;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindPermissionResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * 角色权限限制服务类
 *
 * @author: benben
 * @time: 2025/6/9 下午10:34
 */
public interface SysRoleLimitService extends IService<SysRoleLimit>, SysRoleLimitServiceApi {

    /**
     * 获取角色绑定的权限限制列表
     */
    RoleBindPermissionResponse getRoleLimit(RoleBindPermissionRequest roleBindPermissionRequest);

    /**
     * 更新角色下绑定的权限限制
     */
    void updateRoleBindLimit(RoleBindPermissionRequest roleBindPermissionRequest);

    /**
     * 更新角色的权限范围
     *
     * @param roleId           角色ID
     * @param menuIdList       菜单ID
     * @param menuOptionIdList 菜单功能ID
     */
    void updateRoleLimit(Long roleId, Set<Long> menuIdList, Set<Long> menuOptionIdList);

}
