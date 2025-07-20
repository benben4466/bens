package cn.ibenbeni.bens.sys.modular.role.service;

import cn.ibenbeni.bens.sys.api.callback.RemoveMenuCallbackApi;
import cn.ibenbeni.bens.sys.api.callback.RemoveRoleCallbackApi;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenuDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单关联服务类
 *
 * @author: benben
 * @time: 2025/6/3 下午9:20
 */
public interface SysRoleMenuService extends IService<SysRoleMenuDO>, RemoveRoleCallbackApi, RemoveMenuCallbackApi {

    /**
     * 给角色绑定某些菜单
     *
     * @param roleId 角色ID
     * @param menuId 菜单ID
     */
    void bindRoleMenus(Long roleId, Long menuId);

    /**
     * 根据角色ID，获取角色绑定的菜单列表
     *
     * @param roleId 角色ID
     */
    List<SysRoleMenuDO> getListByRoleId(Long roleId);

    /**
     * 根据指定角色ID，获取角色绑定的菜单列表
     *
     * @param roleIdSet 角色ID集合
     */
    List<SysRoleMenuDO> getListByRoleId(Set<Long> roleIdSet);

    /**
     * 根据菜单ID，获取菜单列表
     *
     * @param menuId 菜单ID
     */
    List<SysRoleMenuDO> getListByMenuId(Long menuId);

    /**
     * 删除指定角色ID下，所有菜单关联关系
     *
     * @param roleId 角色ID
     */
    void deleteListByRoleId(Long roleId);

    /**
     * 删除指定菜单ID下，所有角色的关联关系
     *
     * @param menuId 菜单ID
     */
    void deleteListByMenuId(Long menuId);

    /**
     * 删除指定角色ID下，菜单ID集合
     *
     * @param roleId    角色ID
     * @param menuIdSet 菜单ID集合
     */
    void deleteListByRoleIdAndMenuIds(Long roleId, Set<Long> menuIdSet);

}
