package cn.ibenbeni.bens.sys.modular.menu.service;

import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenu;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统菜单服务类
 *
 * @author: benben
 * @time: 2025/6/1 上午10:51
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 新增
     */
    void add(SysMenuRequest sysMenuRequest);

    /**
     * 删除
     */
    void del(SysMenuRequest sysMenuRequest);

    /**
     * 编辑
     */
    void edit(SysMenuRequest sysMenuRequest);

    /**
     * 查询详情
     */
    SysMenu detail(SysMenuRequest sysMenuRequest);

    /**
     * 获取所有的菜单信息，用在角色绑定权限界面
     */
    List<SysMenu> getTotalMenus();

    /**
     * 获取所有的菜单信息，用在角色绑定权限界面
     *
     * @param limitMenuIds 指定筛选的菜单的范围
     */
    List<SysMenu> getTotalMenus(Set<Long> limitMenuIds);

    /**
     * 调整菜单上下级结构和菜单的顺序
     */
    void updateMenuTree(SysMenuRequest sysMenuRequest);

    /**
     * 获取所有菜单id
     * <p>一般用在项目启动，管理员自动绑定所有菜单</p>
     */
    List<SysMenu> getTotalMenuList();

    /**
     * 获取菜单对应的菜单编码，以及菜单ID、父级ID，菜单名称、菜单图标、菜单是否隐藏、菜单激活地址、菜单路由、组件路径、排序信息
     * <p>用在用户登录后，获取用户首页信息接口</p>
     */
    List<SysMenu> getIndexMenuInfoList(List<Long> menuIdList);

    /**
     * 通过菜单ID，获取菜单的编码集合
     */
    List<String> getMenuCodeList(List<Long> menuIdList);

    /**
     * 获取所有菜单的ID和父级ID的映射关系
     * @return 菜单ID和父级ID的映射关系
     */
    Map<Long, Long> getMenuIdParentIdMap();

}
