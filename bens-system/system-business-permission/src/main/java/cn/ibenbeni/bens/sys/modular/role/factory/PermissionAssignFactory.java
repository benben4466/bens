package cn.ibenbeni.bens.sys.modular.role.factory;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.tree.factory.DefaultTreeBuildFactory;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenu;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuOptions;
import cn.ibenbeni.bens.sys.modular.role.enums.PermissionNodeTypeEnum;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindPermissionItem;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindPermissionResponse;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限分配相关的实体创建
 *
 * @author: benben
 * @time: 2025/6/9 下午10:40
 */
public class PermissionAssignFactory {

    /**
     * 将空状态的权限树，填充角色绑定的权限
     *
     * @param roleBindPermissionResponse 空状态的角色权限树（未设置选中状态）
     * @param rolePermissions            角色所拥有的菜单ID和功能ID的集合
     */
    public static RoleBindPermissionResponse fillCheckedFlag(RoleBindPermissionResponse roleBindPermissionResponse, Set<Long> rolePermissions) {
        List<RoleBindPermissionItem> permissionList = roleBindPermissionResponse.getPermissionList();

        // 开始填充菜单和功能的选中状态
        fillSubItemCheckedFlag(permissionList, rolePermissions);

        // 填充全选的选中状态
        roleBindPermissionResponse.setChecked(true);
        for (RoleBindPermissionItem roleBindPermissionItem : permissionList) {
            if (!roleBindPermissionItem.getChecked()) {
                roleBindPermissionResponse.setChecked(false);
            }
        }

        return roleBindPermissionResponse;
    }

    /**
     * 填充子节点的选中状态
     * <p>根据执行的角色权限参数匹配判断</p>
     *
     * @param beFilled           待填充的节点列表
     * @param rolePermissionList 角色所拥有的菜单和菜单功能ID集合
     */
    private static void fillSubItemCheckedFlag(List<RoleBindPermissionItem> beFilled, Set<Long> rolePermissionList) {
        if (ObjectUtil.hasEmpty(beFilled, rolePermissionList)) {
            return;
        }

        for (RoleBindPermissionItem roleBindPermissionItem : beFilled) {
            if (rolePermissionList.contains(Convert.toLong(roleBindPermissionItem.getNodeId()))) {
                roleBindPermissionItem.setChecked(true);
            }

            // 填充子节点的选中状态
            fillSubItemCheckedFlag(roleBindPermissionItem.getChildren(), rolePermissionList);
        }
    }

    /**
     * 创建权限绑定的菜单列表
     * <p>菜单必须是最子节点，也就是叶子节点</p>
     *
     * @param sysMenus 待构建菜单列表
     */
    public static List<RoleBindPermissionItem> createPermissionMenus(List<SysMenu> sysMenus) {
        if (CollUtil.isEmpty(sysMenus)) {
            return new ArrayList<>();
        }

        // 搜集所有的父级菜单ID
        Set<Long> totalParentMenuId = sysMenus.stream()
                .map(SysMenu::getMenuParentId)
                .collect(Collectors.toSet());

        // 通过父级菜单，筛选出来所有的叶子节点（如果菜单不存在父级菜单里，则代表是叶子节点）
        LinkedHashSet<SysMenu> leafMenus = sysMenus.stream()
                .filter(item -> !totalParentMenuId.contains(item.getMenuId()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        // 叶子节点转化为RoleBindPermissionItem结构
        List<RoleBindPermissionItem> roleBindPermissionItems = new ArrayList<>();
        for (SysMenu leafMenu : leafMenus) {
            RoleBindPermissionItem roleBindPermissionItem = new RoleBindPermissionItem(
                    leafMenu.getMenuId(),
                    -1L,
                    leafMenu.getMenuName(),
                    PermissionNodeTypeEnum.MENU.getCode(),
                    false
            );
            roleBindPermissionItems.add(roleBindPermissionItem);
        }

        return roleBindPermissionItems;
    }

    /**
     * 创建菜单功能信息
     */
    public static List<RoleBindPermissionItem> createMenuOptions(List<SysMenuOptions> sysMenuOptionsList) {
        if (CollUtil.isEmpty(sysMenuOptionsList)) {
            return new ArrayList<>();
        }

        List<RoleBindPermissionItem> optionsResult = new ArrayList<>();
        // 封装响应结果
        for (SysMenuOptions sysMenuOptions : sysMenuOptionsList) {
            RoleBindPermissionItem roleBindPermissionItem = new RoleBindPermissionItem(
                    sysMenuOptions.getMenuOptionId(),
                    sysMenuOptions.getMenuId(),
                    sysMenuOptions.getOptionName(),
                    PermissionNodeTypeEnum.OPTIONS.getCode(),
                    false
            );
            optionsResult.add(roleBindPermissionItem);
        }
        return optionsResult;
    }

    /**
     * 组合成角色绑定权限需要的详情信息，一颗树形结构，选中状态都是未选中
     *
     * @param menus       菜单集合
     * @param menuOptions 菜单功能集合
     */
    public static RoleBindPermissionResponse composeSelectStructure(List<RoleBindPermissionItem> menus, List<RoleBindPermissionItem> menuOptions) {
        RoleBindPermissionResponse roleBindPermissionResponse = new RoleBindPermissionResponse();
        roleBindPermissionResponse.setChecked(false);

        menus.addAll(menuOptions);
        // 合并菜单和菜单功能，并构建树形结构
        List<RoleBindPermissionItem> roleBindPermissionItems = new DefaultTreeBuildFactory<RoleBindPermissionItem>()
                .doTreeBuild(menus);
        roleBindPermissionResponse.setPermissionList(roleBindPermissionItems);

        return roleBindPermissionResponse;
    }

}
