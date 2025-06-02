package cn.ibenbeni.bens.sys.modular.menu.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.sys.api.callback.RemoveMenuCallbackApi;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuOptions;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuOptionsRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 菜单下的功能操作服务类
 *
 * @author: benben
 * @time: 2025/6/2 上午9:53
 */
public interface SysMenuOptionsService extends IService<SysMenuOptions>, RemoveMenuCallbackApi {

    /**
     * 新增
     */
    void add(SysMenuOptionsRequest sysMenuOptionsRequest);

    /**
     * 删除
     */
    void del(SysMenuOptionsRequest sysMenuOptionsRequest);

    /**
     * 编辑
     */
    void edit(SysMenuOptionsRequest sysMenuOptionsRequest);

    /**
     * 查询详情
     */
    SysMenuOptions detail(SysMenuOptionsRequest sysMenuOptionsRequest);

    /**
     * 获取列表
     */
    List<SysMenuOptions> findList(SysMenuOptionsRequest sysMenuOptionsRequest);

    /**
     * 获取列表（带分页）
     */
    PageResult<SysMenuOptions> findPage(SysMenuOptionsRequest sysMenuOptionsRequest);

    /**
     * 获取所有的菜单功能ID
     * <p>一般用在项目启动管理员角色绑定所有的菜单功能</p>
     */
    List<SysMenuOptions> getTotalMenuOptionsList();

    /**
     * 获取所有的菜单功能ID
     * <p>一般用在项目启动管理员角色绑定所有的菜单功能</p>
     */
    List<SysMenuOptions> getTotalMenuOptionsList(Set<Long> roleLimitMenuIdsAndOptionIds);

    /**
     * 获取功能编码集合，通过功能ID集合
     */
    List<String> getOptionsCodeList(List<Long> optionsIdList);

}
