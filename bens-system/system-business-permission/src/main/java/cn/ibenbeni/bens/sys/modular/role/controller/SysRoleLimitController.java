package cn.ibenbeni.bens.sys.modular.role.controller;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindPermissionRequest;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindPermissionResponse;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleLimitService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 角色权限限制控制器
 * <p>除了超级管理员拥有权限绑定外，如果其他角色的成员也需要绑定权限，那么需要在权限绑定界面绑定权限限制</p>
 *
 * @author: benben
 * @time: 2025/6/10 下午3:14
 */
@RestController
public class SysRoleLimitController {

    @Resource
    private SysRoleLimitService sysRoleLimitService;

    /**
     * 获取角色的权限限制列表，和角色绑定权限界面返回的数据结构一样
     */
    @GetMapping("/roleLimit/getRoleLimit")
    public ResponseData<RoleBindPermissionResponse> getRoleBindLimit(@Validated(BaseRequest.detail.class) RoleBindPermissionRequest roleBindPermissionRequest) {
        RoleBindPermissionResponse roleLimit = sysRoleLimitService.getRoleLimit(roleBindPermissionRequest);
        return new SuccessResponseData<>(roleLimit);
    }

    /**
     * 绑定角色的限制列表
     */
    @PostMapping("/roleLimit/bindRoleLimit")
    public ResponseData<?> bindRoleLimit(@RequestBody @Validated(RoleBindPermissionRequest.roleBindPermission.class) RoleBindPermissionRequest roleBindPermissionRequest) {
        sysRoleLimitService.updateRoleBindLimit(roleBindPermissionRequest);
        return new SuccessResponseData<>();
    }

}
