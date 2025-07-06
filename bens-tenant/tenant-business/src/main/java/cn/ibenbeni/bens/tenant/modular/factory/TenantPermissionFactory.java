package cn.ibenbeni.bens.tenant.modular.factory;

import cn.ibenbeni.bens.tenant.modular.entity.SysTenantPackage;

import java.util.HashSet;
import java.util.Set;

/**
 * 租户权限工厂类
 *
 * @author: benben
 * @time: 2025/7/1 下午10:36
 */
public class TenantPermissionFactory {

    /**
     * 租户套餐实体填充菜单及菜单功能
     */
    public static void fillPermissionMenus(SysTenantPackage tenantPackage, List<RoleBindPermissionItem> permissionList) {
        Set<Long> menuSet = new HashSet<>();
        Set<Long> menuOptionSet = new HashSet<>();
        for (RoleBindPermissionItem permissionItem : permissionList) {
            if (PermissionNodeTypeEnum.MENU.getCode().equals(permissionItem.getPermissionNodeType())) {
                menuSet.add(Long.valueOf(permissionItem.getNodeId()));
            } else if (PermissionNodeTypeEnum.OPTIONS.getCode().equals(permissionItem.getPermissionNodeType())) {
                menuOptionSet.add(Long.valueOf(permissionItem.getNodeId()));
            }
        }

        tenantPackage.setPackageMenuIds(menuSet);
        tenantPackage.setPackageMenuOptionIds(menuOptionSet);
    }

    /**
     * 获取租户套餐的菜单ID
     *
     * @param permissionList 菜单权限列表
     */
    public static Set<Long> getTenantPackageMenuIds(List<RoleBindPermissionItem> permissionList) {
        Set<Long> menuSet = new HashSet<>();
        for (RoleBindPermissionItem permissionItem : permissionList) {
            if (PermissionNodeTypeEnum.MENU.getCode().equals(permissionItem.getPermissionNodeType())) {
                menuSet.add(Long.valueOf(permissionItem.getNodeId()));
            }
        }
        return menuSet;
    }

    /**
     * 获取租户套餐的菜单功能ID
     *
     * @param permissionList 菜单权限列表
     */
    public static Set<Long> getTenantPackageMenuOptionIds(List<RoleBindPermissionItem> permissionList) {
        Set<Long> menuOptionSet = new HashSet<>();
        for (RoleBindPermissionItem permissionItem : permissionList) {
            if (PermissionNodeTypeEnum.OPTIONS.getCode().equals(permissionItem.getPermissionNodeType())) {
                menuOptionSet.add(Long.valueOf(permissionItem.getNodeId()));
            }
        }
        return menuOptionSet;
    }

}
