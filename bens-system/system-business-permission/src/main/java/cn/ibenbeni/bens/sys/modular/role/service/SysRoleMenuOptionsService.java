package cn.ibenbeni.bens.sys.modular.role.service;

import cn.ibenbeni.bens.sys.api.callback.RemoveMenuCallbackApi;
import cn.ibenbeni.bens.sys.api.callback.RemoveRoleCallbackApi;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuOptions;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenuOptions;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色和菜单下的功能关联服务类
 *
 * @author: benben
 * @time: 2025/6/8 下午4:31
 */
public interface SysRoleMenuOptionsService extends IService<SysRoleMenuOptions>, RemoveRoleCallbackApi, RemoveMenuCallbackApi {

    /**
     * 删除角色绑定的菜单功能
     *
     * @param optionsId 菜单功能ID
     */
    void removeRoleBindOptions(Long optionsId);

    /**
     * 给角色绑定菜单功能
     *
     * @param roleId             角色ID
     * @param sysMenuOptionsList 被绑定菜单功能ID
     */
    void bindRoleMenuOptions(Long roleId, List<SysMenuOptions> sysMenuOptionsList);

    /**
     * 获取角色绑定的菜单功能ID
     *
     * @param roleIdList 角色ID集合
     */
    List<Long> getRoleBindMenuOptionsIdList(List<Long> roleIdList, boolean prioritizeCaching);

}
