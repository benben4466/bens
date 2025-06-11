package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenu;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuOptions;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuOptionsService;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuService;
import cn.ibenbeni.bens.sys.modular.role.factory.PermissionAssignFactory;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindPermissionItem;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindPermissionResponse;
import cn.ibenbeni.bens.sys.modular.role.service.PermissionAssignService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuOptionsService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色权限绑定业务实现类
 *
 * @author: benben
 * @time: 2025/6/8 下午5:06
 */
@Service
public class PermissionAssignServiceImpl implements PermissionAssignService {

    @Resource
    private SysRoleMenuOptionsService sysRoleMenuOptionsService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysMenuOptionsService sysMenuOptionsService;

    @Override
    public RoleBindPermissionResponse createSelectTreeStructure() {
        return this.createSelectTreeStructure(null);
    }

    @Override
    public RoleBindPermissionResponse createSelectTreeStructure(Set<Long> limitMenuIdsAndOptionIds) {

        // 获取菜单ID集合
        List<SysMenu> sysMenus = sysMenuService.getTotalMenus(limitMenuIdsAndOptionIds);
        // 组装所有的叶子节点菜单【初始化菜单】
        List<RoleBindPermissionItem> totalResultMenus = PermissionAssignFactory.createPermissionMenus(sysMenus);

        // 获取所有的菜单上的功能
        Set<Long> menuIds = totalResultMenus.stream()
                .map(item -> Long.valueOf(item.getNodeId()))
                .collect(Collectors.toSet());
        LambdaQueryWrapper<SysMenuOptions> optionsLambdaQueryWrapper = Wrappers.lambdaQuery(SysMenuOptions.class)
                .in(SysMenuOptions::getMenuId, menuIds)
                .in(CollUtil.isNotEmpty(limitMenuIdsAndOptionIds), SysMenuOptions::getMenuOptionId, limitMenuIdsAndOptionIds)
                .select(SysMenuOptions::getMenuId, SysMenuOptions::getMenuOptionId, SysMenuOptions::getOptionName);
        List<SysMenuOptions> sysMenuOptionsList = sysMenuOptionsService.list(optionsLambdaQueryWrapper);

        // 组装所有的应用节点信息【初始化菜单功能】
        List<RoleBindPermissionItem> totalResultMenuOptions = PermissionAssignFactory.createMenuOptions(sysMenuOptionsList);

        // 将菜单、功能组成返回结果
        return PermissionAssignFactory.composeSelectStructure(totalResultMenus, totalResultMenuOptions);
    }

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
