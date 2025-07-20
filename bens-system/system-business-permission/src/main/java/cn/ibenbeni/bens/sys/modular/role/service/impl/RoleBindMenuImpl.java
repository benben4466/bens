package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuOptions;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuOptionsService;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuService;
import cn.ibenbeni.bens.sys.modular.role.action.RoleAssignOperateAction;
import cn.ibenbeni.bens.sys.modular.role.action.RoleBindLimitAction;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleLimit;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenuDO;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenuOptions;
import cn.ibenbeni.bens.sys.modular.role.enums.RoleLimitTypeEnum;
import cn.ibenbeni.bens.sys.modular.role.factory.MenuPathCalcFactory;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindPermissionRequest;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleLimitService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuOptionsService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuService;
import cn.ibenbeni.bens.sys.modular.role.util.AssertAssignUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色绑定菜单的相关操作
 *
 * @author: benben
 * @time: 2025/6/10 上午10:18
 */
@Service
public class RoleBindMenuImpl implements RoleAssignOperateAction, RoleBindLimitAction {

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
        return PermissionNodeTypeEnum.MENU;
    }

    @Override
    public void doOperateAction(RoleBindPermissionRequest roleBindPermissionRequest, Set<Long> roleLimitMenuIdsAndOptionIds) {
        Long roleId = roleBindPermissionRequest.getRoleId();
        Long menuId = roleBindPermissionRequest.getNodeId();

        // 待授权菜单ID，未在角色权限列表中，则返回，即非法操作，即角色未拥有此权限
        if (ObjectUtil.isNotEmpty(roleLimitMenuIdsAndOptionIds) && !roleLimitMenuIdsAndOptionIds.contains(menuId)) {
            return;
        }

        // 获取菜单的所有ID和parentId，后续判断删除的节点的父级没有其他自己节点时候使用
        Map<Long, Long> menuIdParentIdMap = sysMenuService.getMenuIdParentIdMap();
        Set<Long> needToDelete = MenuPathCalcFactory.getMenuParentIds(menuId, menuIdParentIdMap);

        // 1 清除旧菜单和角色关联数据
        LambdaQueryWrapper<SysRoleMenuDO> sysRoleMenuLambdaQueryWrapper = Wrappers.lambdaQuery(SysRoleMenuDO.class)
                .eq(SysRoleMenuDO::getRoleId, roleId)
                .in(SysRoleMenuDO::getMenuId, needToDelete);
        sysRoleMenuService.remove(sysRoleMenuLambdaQueryWrapper);

        // 1.2 如果是选中，则执行菜单和角色的绑定
        if (roleBindPermissionRequest.getChecked()) {
            SysRoleMenuDO sysRoleMenuDO = new SysRoleMenuDO();
            sysRoleMenuDO.setRoleId(roleId);
            sysRoleMenuDO.setMenuId(menuId);
            sysRoleMenuService.save(sysRoleMenuDO);
        }

        // 2 查询菜单下的所有菜单功能
        List<Long> menuOptions = this.getMenuOptions(menuId, roleLimitMenuIdsAndOptionIds);
        if (CollUtil.isEmpty(menuOptions)) {
            return;
        }

        // 2.1 如果有菜单功能，则先删除旧数据，后添加的
        LambdaQueryWrapper<SysRoleMenuOptions> roleMenuOptionsLambdaQueryWrapper = Wrappers.lambdaQuery(SysRoleMenuOptions.class)
                .eq(SysRoleMenuOptions::getRoleId, roleId)
                .in(SysRoleMenuOptions::getMenuOptionId, menuOptions);
        AssertAssignUtil.assertAssign(roleId, roleMenuOptionsLambdaQueryWrapper);
        sysRoleMenuOptionsService.remove(roleMenuOptionsLambdaQueryWrapper);

        // 2.2 如果是选中状态，则从新进行这些角色和功能的绑定
        if (roleBindPermissionRequest.getChecked()) {
            ArrayList<SysRoleMenuOptions> sysRoleMenuOptions = new ArrayList<>();
            for (Long menuOptionId : menuOptions) {
                SysRoleMenuOptions roleMenuOptions = new SysRoleMenuOptions();
                roleMenuOptions.setRoleId(roleId);
                roleMenuOptions.setMenuId(menuId);
                roleMenuOptions.setMenuOptionId(menuOptionId);
                sysRoleMenuOptions.add(roleMenuOptions);
            }

            sysRoleMenuOptionsService.saveBatch(sysRoleMenuOptions);
        }

    }

    @Override
    public PermissionNodeTypeEnum getRoleBindLimitNodeType() {
        return this.getNodeType();
    }

    @Override
    public void doRoleBindLimitAction(RoleBindPermissionRequest roleBindPermissionRequest) {
        Long roleId = roleBindPermissionRequest.getRoleId();
        Long menuId = roleBindPermissionRequest.getNodeId();

        List<SysRoleLimit> sysRoleLimitTotal = new ArrayList<>();

        // 1 先取消绑定，角色对菜单的限制
        LambdaQueryWrapper<SysRoleLimit> menuRemoveWrapper = Wrappers.lambdaQuery(SysRoleLimit.class)
                .eq(SysRoleLimit::getRoleId, roleId)
                .eq(SysRoleLimit::getBusinessId, menuId);
        sysRoleLimitService.remove(menuRemoveWrapper);

        // 2 如果是选中，则执行角色绑定菜单限制
        if (roleBindPermissionRequest.getChecked()) {
            SysRoleLimit roleLimit = new SysRoleLimit();
            roleLimit.setRoleId(roleId);
            roleLimit.setBusinessId(menuId);
            roleLimit.setLimitType(RoleLimitTypeEnum.MENU.getCode());
            sysRoleLimitTotal.add(roleLimit);
        }

        // 2.1 查询菜单下的所有菜单功能
        List<Long> menuOptionsIds = this.getMenuOptions(menuId);

        // 菜单下没有菜单功能，则直接返回
        if (CollUtil.isEmpty(menuOptionsIds)) {
            sysRoleLimitService.saveBatch(sysRoleLimitTotal);
            return;
        }

        // 2.2 如果有菜单功能，则先删除旧数据，再重新绑定
        // 删除旧数据
        LambdaQueryWrapper<SysRoleLimit> menuOptionsRemoveWrapper = Wrappers.lambdaQuery(SysRoleLimit.class)
                .eq(SysRoleLimit::getRoleId, roleId)
                .in(SysRoleLimit::getBusinessId, menuOptionsIds);
        sysRoleLimitService.remove(menuOptionsRemoveWrapper);

        // 2.3 如果是选中，则创建角色对菜单功能的绑定限制
        if (roleBindPermissionRequest.getChecked()) {
            for (Long menuOptionId : menuOptionsIds) {
                SysRoleLimit sysRoleLimit = new SysRoleLimit();
                sysRoleLimit.setRoleId(roleId);
                sysRoleLimit.setLimitType(RoleLimitTypeEnum.MENU_OPTIONS.getCode());
                sysRoleLimit.setBusinessId(menuOptionId);
                sysRoleLimitTotal.add(sysRoleLimit);
            }
        }

        sysRoleLimitService.saveBatch(sysRoleLimitTotal);
    }

    /**
     * 获取菜单下的所有菜单功能ID列表
     *
     * @param menuId 菜单ID
     */
    private List<Long> getMenuOptions(Long menuId) {
        return this.getMenuOptions(menuId, null);
    }

    /**
     * 获取菜单下的所有菜单功能
     *
     * @param menuId                       菜单ID
     * @param roleLimitMenuIdsAndOptionIds 限制菜单功能ID列表
     */
    private List<Long> getMenuOptions(Long menuId, Set<Long> roleLimitMenuIdsAndOptionIds) {
        LambdaQueryWrapper<SysMenuOptions> queryWrapper = Wrappers.lambdaQuery(SysMenuOptions.class)
                .eq(SysMenuOptions::getMenuId, menuId)
                .in(CollUtil.isNotEmpty(roleLimitMenuIdsAndOptionIds), SysMenuOptions::getMenuOptionId, roleLimitMenuIdsAndOptionIds)
                .select(SysMenuOptions::getMenuOptionId);
        List<SysMenuOptions> list = sysMenuOptionsService.list(queryWrapper);
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream()
                .map(SysMenuOptions::getMenuOptionId)
                .collect(Collectors.toList());
    }

}
