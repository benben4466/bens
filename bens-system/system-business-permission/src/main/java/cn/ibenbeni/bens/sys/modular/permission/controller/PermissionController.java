package cn.ibenbeni.bens.sys.modular.permission.controller;

import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.sys.modular.permission.pojo.req.PermissionAssignRoleMenuReq;
import cn.ibenbeni.bens.sys.modular.permission.pojo.req.PermissionAssignUserRoleReq;
import cn.ibenbeni.bens.sys.modular.permission.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Tag(name = "管理后台 - 权限")
@RestController
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @Operation(summary = "赋予角色菜单")
    @PostMapping("/system/permission/assign-role-menu")
    public ResponseData<Boolean> assignRoleMenu(@RequestBody @Validated PermissionAssignRoleMenuReq req) {
        permissionService.assignRoleMenu(req.getRoleId(), req.getMenuIds());
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "赋予用户角色")
    @PostMapping("/system/permission/assign-user-role")
    public ResponseData<Boolean> assignUserRole(@RequestBody @Validated PermissionAssignUserRoleReq req) {
        permissionService.assignUserRole(req.getUserId(), req.getRoleIds());
        return new SuccessResponseData<>(true);
    }

}
