package cn.ibenbeni.bens.sys.modular.role.controller;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleDO;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindDataScopeRequest;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindPermissionRequest;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.SysRoleRequest;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindDataScopeResponse;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindPermissionResponse;
import cn.ibenbeni.bens.sys.modular.role.service.PermissionAssignService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleDataScopeService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限分配界面的接口
 *
 * @author: benben
 * @time: 2025/6/8 下午3:07
 */
@RestController
public class PermissionAssignController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleDataScopeService sysRoleDataScopeService;

    @Resource
    private PermissionAssignService permissionAssignService;

    /**
     * 获取所有角色列表
     * <p>用在权限分配界面，左侧的角色列表</p>
     */
    @GetMapping("/permission/getRoleList")
    public ResponseData<List<SysRoleDO>> getRoleList(SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData<>(sysRoleService.permissionGetRoleList(sysRoleRequest));
    }

    /**
     * 获取角色绑定的权限列表
     * <p>角色绑定的权限列表返回的是一个树形结构：第1层是应用下的菜单，第2层是菜单下的菜单功能</p>
     */
    @GetMapping("/permission/getRoleBindPermission")
    public ResponseData<RoleBindPermissionResponse> getRoleBindPermission(@Validated(BaseRequest.detail.class) RoleBindPermissionRequest roleBindPermissionRequest) {
        RoleBindPermissionResponse roleBindPermission = permissionAssignService.getRoleBindPermission(roleBindPermissionRequest);
        return new SuccessResponseData<>(roleBindPermission);
    }

    /**
     * 更新角色绑定权限
     * <p>每点击一个权限直接调用一次接口，实时保存</p>
     */
    @PostMapping("/permission/updateRoleBindPermission")
    public ResponseData<?> updateRoleBindPermission(@RequestBody @Validated(RoleBindPermissionRequest.roleBindPermission.class) RoleBindPermissionRequest roleBindPermissionRequest) {
        permissionAssignService.updateRoleBindPermission(roleBindPermissionRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 获取角色的数据权限详情
     */
    @GetMapping("/permission/getRoleBindDataScope")
    public ResponseData<RoleBindDataScopeResponse> getRoleBindDataScope(@Validated(BaseRequest.detail.class) RoleBindDataScopeRequest roleBindDataScopeRequest) {
        RoleBindDataScopeResponse roleBindDataScopeResponse = sysRoleDataScopeService.getRoleBindDataScope(roleBindDataScopeRequest);
        return new SuccessResponseData<>(roleBindDataScopeResponse);
    }

    /**
     * 角色绑定数据权限的配置
     */
    @PostMapping("/permission/updateRoleBindDataScope")
    public ResponseData<?> updateRoleBindDataScope(@RequestBody @Validated(RoleBindDataScopeRequest.roleBindDataScope.class) RoleBindDataScopeRequest roleBindDataScopeRequest) {
        sysRoleDataScopeService.updateRoleBindDataScope(roleBindDataScopeRequest);
        return new SuccessResponseData<>();
    }

}
