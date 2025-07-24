package cn.ibenbeni.bens.sys.modular.menu.service;

import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuDO;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuListReq;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuSaveReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单服务类
 *
 * @author: benben
 * @time: 2025/6/1 上午10:51
 */
public interface SysMenuService extends IService<SysMenuDO> {

    /**
     * 创建菜单
     *
     * @return 菜单ID
     */
    Long createMenu(SysMenuSaveReq req);

    /**
     * 删除菜单
     */
    void deleteMenu(Long menuId);

    /**
     * 批量删除菜单
     */
    void deleteMenu(Set<Long> idSet);

    /**
     * 更新菜单
     */
    void updateMenu(SysMenuSaveReq req);

    /**
     * 获取菜单
     *
     * @param menuId 菜单ID
     */
    SysMenuDO getMenu(Long menuId);

    /**
     * 获取所有菜单列表
     */
    List<SysMenuDO> getMenuList();

    /**
     * 获取菜单列表
     *
     * @param menuIdSet 指定菜单ID集合
     */
    List<SysMenuDO> getMenuList(Set<Long> menuIdSet);

    /**
     * 获取菜单列表
     */
    List<SysMenuDO> getMenuList(SysMenuListReq req);

    /**
     * 过滤掉关闭的菜单及其子菜单
     *
     * @param list 菜单列表
     */
    List<SysMenuDO> filterDisableMenus(List<SysMenuDO> list);

    /**
     * 获取权限对应的菜单编号数组
     */
    List<Long> listMenuIdByPermissionCode(String permissionCode);

}
