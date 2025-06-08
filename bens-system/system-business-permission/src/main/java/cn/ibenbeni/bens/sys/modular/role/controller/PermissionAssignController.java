package cn.ibenbeni.bens.sys.modular.role.controller;

import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindDataScopeRequest;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindDataScopeResponse;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleDataScopeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 权限分配界面的接口
 *
 * @author: benben
 * @time: 2025/6/8 下午3:07
 */
@RestController
public class PermissionAssignController {

    @Resource
    private SysRoleDataScopeService sysRoleDataScopeService;

    /**
     * 获取角色的数据权限详情
     */
    @GetMapping("/permission/getRoleBindDataScope")
    public ResponseData<RoleBindDataScopeResponse> getRoleBindDataScope(RoleBindDataScopeRequest roleBindDataScopeRequest) {
        RoleBindDataScopeResponse roleBindDataScopeResponse = sysRoleDataScopeService.getRoleBindDataScope(roleBindDataScopeRequest);
        return new SuccessResponseData<>(roleBindDataScopeResponse);
    }

    /**
     * 角色绑定数据权限的配置
     */
    @PostMapping("/permission/updateRoleBindDataScope")
    public ResponseData<?> updateRoleBindDataScope(@RequestBody RoleBindDataScopeRequest roleBindDataScopeRequest) {
        sysRoleDataScopeService.updateRoleBindDataScope(roleBindDataScopeRequest);
        return new SuccessResponseData<>();
    }

}
