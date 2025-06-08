package cn.ibenbeni.bens.sys.modular.role.service;

import cn.ibenbeni.bens.sys.api.callback.RemoveMenuCallbackApi;
import cn.ibenbeni.bens.sys.api.callback.RemoveRoleCallbackApi;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenu;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色菜单关联服务类
 *
 * @author: benben
 * @time: 2025/6/3 下午9:20
 */
public interface SysRoleMenuService extends IService<SysRoleMenu>, RemoveRoleCallbackApi, RemoveMenuCallbackApi {

    /**
     * 给角色绑定某些菜单
     *
     * @param roleId   角色ID
     * @param menuList 被绑定菜单集合
     */
    void bindRoleMenus(Long roleId, List<SysMenu> menuList);

    /**
     * 获取角色绑定的菜单ID集合，返回菜单ID的集合
     *
     * @param roleIdList        角色ID集合
     * @param prioritizeCaching 是否优先从缓存中获取数据
     */
    List<Long> getRoleBindMenuIdList(List<Long> roleIdList, boolean prioritizeCaching);

}
