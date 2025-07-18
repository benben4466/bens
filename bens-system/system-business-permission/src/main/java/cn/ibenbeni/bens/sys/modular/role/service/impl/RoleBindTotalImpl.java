package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuDO;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuOptions;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuOptionsService;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuService;
import cn.ibenbeni.bens.sys.modular.role.action.RoleAssignOperateAction;
import cn.ibenbeni.bens.sys.modular.role.action.RoleBindLimitAction;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleLimit;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenu;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenuOptions;
import cn.ibenbeni.bens.sys.api.enums.role.PermissionNodeTypeEnum;
import cn.ibenbeni.bens.sys.modular.role.enums.RoleLimitTypeEnum;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindPermissionRequest;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleLimitService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuOptionsService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 角色绑定权限，点击绑定所有时候的业务处理
 *
 * @author: benben
 * @time: 2025/6/10 上午10:51
 */
@Service
public class RoleBindTotalImpl implements RoleAssignOperateAction, RoleBindLimitAction {

    @Resource
    private SysRoleLimitService sysRoleLimitService;

    @Resource
    private SysRoleMenuOptionsService sysRoleMenuOptionsService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysMenuOptionsService sysMenuOptionsService;

    @Override
    public PermissionNodeTypeEnum getNodeType() {
        return PermissionNodeTypeEnum.TOTAL;
    }

    @Override
    public void doOperateAction(RoleBindPermissionRequest roleBindPermissionRequest, Set<Long> roleLimitMenuIdsAndOptionIds) {
        Long roleId = roleBindPermissionRequest.getRoleId();

        // 1 清空角色绑定的所有菜单和菜单功能
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuLambdaQueryWrapper = Wrappers.lambdaQuery(SysRoleMenu.class)
                .eq(SysRoleMenu::getRoleId, roleId);
        sysRoleMenuService.remove(sysRoleMenuLambdaQueryWrapper);

        LambdaQueryWrapper<SysRoleMenuOptions> sysRoleMenuOptionsLambdaQueryWrapper = Wrappers.lambdaQuery(SysRoleMenuOptions.class)
                .eq(SysRoleMenuOptions::getRoleId, roleId);
        sysRoleMenuOptionsService.remove(sysRoleMenuOptionsLambdaQueryWrapper);

        // 2 如果是选中状态，则从新绑定所有的选项
        if (!roleBindPermissionRequest.getChecked()) {
            return;
        }

        // 2.1 绑定菜单数据
        List<SysMenuDO> totalMenus = sysMenuService.getTotalMenus(roleLimitMenuIdsAndOptionIds);
        List<SysRoleMenu> sysRoleMenuList = new ArrayList<>();
        for (SysMenuDO menuItem : totalMenus) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuItem.getMenuId());
            sysRoleMenuList.add(sysRoleMenu);
        }
        sysRoleMenuService.saveBatch(sysRoleMenuList);

        // 2.2 绑定菜单功能数据
        List<SysMenuOptions> sysMenuOptionsList = sysMenuOptionsService.getTotalMenuOptionsList(roleLimitMenuIdsAndOptionIds);
        List<SysRoleMenuOptions> sysRoleMenuOptionsList = new ArrayList<>();
        for (SysMenuOptions menuOptionItem : sysMenuOptionsList) {
            SysRoleMenuOptions sysRoleMenuOptions = new SysRoleMenuOptions();
            sysRoleMenuOptions.setRoleId(roleId);
            sysRoleMenuOptions.setMenuId(menuOptionItem.getMenuId());
            sysRoleMenuOptions.setMenuOptionId(menuOptionItem.getMenuOptionId());
            sysRoleMenuOptionsList.add(sysRoleMenuOptions);
        }
        sysRoleMenuOptionsService.saveBatch(sysRoleMenuOptionsList);
    }

    @Override
    public PermissionNodeTypeEnum getRoleBindLimitNodeType() {
        return this.getNodeType();
    }

    @Override
    public void doRoleBindLimitAction(RoleBindPermissionRequest roleBindPermissionRequest) {
        Long roleId = roleBindPermissionRequest.getRoleId();

        // 清空该角色所有限制
        LambdaQueryWrapper<SysRoleLimit> clearWrapper = Wrappers.lambdaQuery(SysRoleLimit.class)
                .eq(SysRoleLimit::getRoleId, roleId);
        sysRoleLimitService.remove(clearWrapper);

        // 此处仅处理所有选项都选中的情况
        if (!roleBindPermissionRequest.getChecked()) {
            return;
        }

        // 若是选择状态，则绑定所有选项
        List<SysRoleLimit> sysRoleLimitList = new ArrayList<>();
        // 1 菜单
        List<SysMenuDO> totalMenus = sysMenuService.getTotalMenus();
        for (SysMenuDO menuItem : totalMenus) {
            SysRoleLimit sysRoleLimit = new SysRoleLimit();
            sysRoleLimit.setRoleId(roleId);
            sysRoleLimit.setBusinessId(menuItem.getMenuId());
            sysRoleLimit.setLimitType(RoleLimitTypeEnum.MENU.getCode());
            sysRoleLimitList.add(sysRoleLimit);
        }

        // 2 菜单功能
        List<SysMenuOptions> sysMenuOptionsList = sysMenuOptionsService.getTotalMenuOptionsList();
        for (SysMenuOptions menuOptionItem : sysMenuOptionsList) {
            SysRoleLimit sysRoleLimit = new SysRoleLimit();
            sysRoleLimit.setRoleId(roleId);
            sysRoleLimit.setBusinessId(menuOptionItem.getMenuOptionId());
            sysRoleLimit.setLimitType(RoleLimitTypeEnum.MENU_OPTIONS.getCode());
            sysRoleLimitList.add(sysRoleLimit);
        }

        sysRoleLimitService.saveBatch(sysRoleLimitList);
    }

}
