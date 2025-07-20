package cn.ibenbeni.bens.sys.modular.permission.service.impl;

import cn.ibenbeni.bens.sys.api.SysUserRoleServiceApi;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuOptionsService;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuService;
import cn.ibenbeni.bens.sys.modular.permission.service.PermissionService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuOptionsService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 权限-服务实现接口
 *
 * @author: benben
 * @time: 2025/6/8 下午5:06
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    // region 属性

    @Lazy
    @Resource
    private SysUserRoleServiceApi sysUserRoleServiceApi;

    @Resource
    private SysRoleMenuOptionsService sysRoleMenuOptionsService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysMenuOptionsService sysMenuOptionsService;

    // endregion


}
