package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.auth.api.context.LoginContext;
import cn.ibenbeni.bens.sys.api.SysUserRoleServiceApi;
import cn.ibenbeni.bens.sys.modular.role.action.RoleAssignOperateAction;
import cn.ibenbeni.bens.sys.modular.role.action.RoleBindLimitAction;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleLimit;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenuOptions;
import cn.ibenbeni.bens.sys.api.enums.role.PermissionNodeTypeEnum;
import cn.ibenbeni.bens.sys.modular.role.enums.RoleLimitTypeEnum;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindPermissionRequest;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleLimitService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuOptionsService;
import cn.ibenbeni.bens.sys.modular.role.util.AssertAssignUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 角色绑定功能的限制
 *
 * @author: benben
 * @time: 2025/6/10 上午10:47
 */
@Service
public class RoleBindOptionImpl implements RoleAssignOperateAction, RoleBindLimitAction {

    @Resource
    private SysUserRoleServiceApi sysUserRoleServiceApi;

    @Resource
    private SysRoleLimitService sysRoleLimitService;

    @Resource
    private SysRoleMenuOptionsService sysRoleMenuOptionsService;

    @Override
    public PermissionNodeTypeEnum getNodeType() {
        return PermissionNodeTypeEnum.OPTIONS;
    }

    @Override
    public void doOperateAction(RoleBindPermissionRequest roleBindPermissionRequest, Set<Long> roleLimitMenuIdsAndOptionIds) {
        Long roleId = roleBindPermissionRequest.getRoleId();
        Long menuOptionId = roleBindPermissionRequest.getNodeId();

        // 未拥有添加此菜单功能权限
        if (CollUtil.isNotEmpty(roleLimitMenuIdsAndOptionIds) && !roleLimitMenuIdsAndOptionIds.contains(menuOptionId)) {
            return;
        }

        // 选中，代表添加，否则删除
        if (roleBindPermissionRequest.getChecked()) {
            SysRoleMenuOptions sysRoleMenuOptions = new SysRoleMenuOptions();
            sysRoleMenuOptions.setRoleId(roleId);
            sysRoleMenuOptions.setRoleMenuOptionId(menuOptionId);
            sysRoleMenuOptionsService.save(sysRoleMenuOptions);
        } else {
            Long userId = LoginContext.me().getLoginUser().getUserId();
            List<Long> userRoleIdList = sysUserRoleServiceApi.getUserRoleIdList(userId);
            if (userRoleIdList.contains(roleId) && menuOptionId.equals(AssertAssignUtil.DISABLED_MENU_OPTIONS)) {
                return;
            }

            LambdaQueryWrapper<SysRoleMenuOptions> removeWrapper = Wrappers.lambdaQuery(SysRoleMenuOptions.class)
                    .eq(SysRoleMenuOptions::getRoleId, roleId)
                    .eq(SysRoleMenuOptions::getMenuOptionId, menuOptionId);
            sysRoleMenuOptionsService.remove(removeWrapper);
        }
    }

    @Override
    public PermissionNodeTypeEnum getRoleBindLimitNodeType() {
        return this.getNodeType();
    }

    @Override
    public void doRoleBindLimitAction(RoleBindPermissionRequest roleBindPermissionRequest) {
        Long roleId = roleBindPermissionRequest.getRoleId();
        Long menuOptionId = roleBindPermissionRequest.getNodeId();

        // 选中，代表添加限制，否则删除
        if (roleBindPermissionRequest.getChecked()) {
            SysRoleLimit sysRoleLimit = new SysRoleLimit();
            sysRoleLimit.setRoleId(roleId);
            sysRoleLimit.setBusinessId(menuOptionId);
            sysRoleLimit.setLimitType(RoleLimitTypeEnum.MENU_OPTIONS.getCode());
            sysRoleLimitService.save(sysRoleLimit);
        } else {
            LambdaQueryWrapper<SysRoleLimit> removeWrapper = Wrappers.lambdaQuery(SysRoleLimit.class)
                    .eq(SysRoleLimit::getRoleId, roleId)
                    .eq(SysRoleLimit::getBusinessId, menuOptionId);
            sysRoleLimitService.remove(removeWrapper);
        }
    }

}
