package cn.ibenbeni.bens.sys.modular.user.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.sys.api.expander.SysConfigExpander;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUser;
import cn.ibenbeni.bens.sys.modular.user.pojo.request.SysUserRequest;
import cn.ibenbeni.bens.sys.modular.user.pojo.request.SysUserRoleRequest;
import cn.ibenbeni.bens.sys.modular.user.service.SysUserRoleService;
import cn.ibenbeni.bens.sys.modular.user.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统用户控制器
 *
 * @author benben
 * @date 2025/5/3  下午7:25
 */
@RestController
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    /**
     * 添加用户
     */
    @PostMapping(value = "/sysUser/add")
    public ResponseData<SysUser> add(@RequestBody SysUserRequest sysUserRequest) {
        sysUserService.add(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除用户
     */
    @PostMapping(value = "/sysUser/delete")
    public ResponseData<?> delete(@RequestBody SysUserRequest sysUserRequest) {
        sysUserService.del(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 批量删除用户
     */
    @PostMapping(value = "/sysUser/batchDelete")
    public ResponseData<?> batchDelete(@RequestBody SysUserRequest sysUserRequest) {
        sysUserService.batchDel(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑用户
     */
    @PostMapping(value = "/sysUser/edit")
    public ResponseData<?> edit(@RequestBody SysUserRequest sysUserRequest) {
        sysUserService.edit(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看用户详情
     */
    @GetMapping(value = "/sysUser/detail")
    public ResponseData<SysUser> detail(SysUserRequest sysUserRequest) {
        return new SuccessResponseData<>(sysUserService.detail(sysUserRequest));
    }

    /**
     * 获取列表-用户信息（带分页）
     */
    @GetMapping(value = "/sysUser/page")
    public ResponseData<PageResult<SysUser>> page(SysUserRequest sysUserRequest) {
        return new SuccessResponseData<>(sysUserService.findPage(sysUserRequest));
    }

    /**
     * 修改用户状态
     */
    @PostMapping(value = "/sysUser/updateStatus")
    public ResponseData<?> updateStatus(@RequestBody SysUserRequest sysUserRequest) {
        sysUserService.updateStatus(sysUserRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 重置用户密码为默认密码
     */
    @PostMapping(value = "/sysUser/resetPassword")
    public ResponseData<?> resetPassword(@RequestBody SysUserRequest sysUserRequest) {
        // 获取系统配置的默认密码
        String password = SysConfigExpander.getDefaultPassWord();
        sysUserService.resetPassword(sysUserRequest.getUserId(), password);
        return new SuccessResponseData<>();
    }

    /**
     * 绑定用户角色
     * <p>组织架构-人员页面使用；用于绑定系统角色</p>
     */
    @PostMapping("/sysUser/bindRoles")
    public ResponseData<?> bindRoles(@RequestBody SysUserRoleRequest sysUserRoleRequest) {
        sysUserRoleService.bindRoles(sysUserRoleRequest);
        return new SuccessResponseData<>();
    }

}
