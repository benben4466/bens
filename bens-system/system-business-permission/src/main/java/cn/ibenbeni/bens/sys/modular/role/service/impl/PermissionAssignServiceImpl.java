package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.ibenbeni.bens.sys.modular.role.service.PermissionAssignService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuOptionsService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuService;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色权限绑定业务实现类
 *
 * @author: benben
 * @time: 2025/6/8 下午5:06
 */
public class PermissionAssignServiceImpl implements PermissionAssignService {

    @Resource
    private SysRoleMenuOptionsService sysRoleMenuOptionsService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public Set<Long> getRoleBindMenusAndOptions(Long roleId) {
        Set<Long> resultPermissions = new HashSet<>();
        if (roleId == null) {
            return resultPermissions;
        }

        // 获取角色绑定的菜单
        List<Long> sysRoleMenuList = sysRoleMenuService.getRoleBindMenuIdList(ListUtil.list(false, roleId), false);
        resultPermissions.addAll(sysRoleMenuList);

        // 获取角色绑定的功能
        List<Long> optionsIdsList = sysRoleMenuOptionsService.getRoleBindMenuOptionsIdList(ListUtil.list(false, roleId), false);
        resultPermissions.addAll(optionsIdsList);

        return resultPermissions;
    }

}
