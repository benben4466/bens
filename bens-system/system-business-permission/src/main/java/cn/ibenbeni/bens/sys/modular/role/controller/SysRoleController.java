package cn.ibenbeni.bens.sys.modular.role.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRole;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.SysRoleRequest;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统角色控制器
 *
 * @author benben
 * @date 2025/5/25  下午4:06
 */
@RestController
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 添加角色
     */
    @PostMapping(value = "/sysRole/add")
    public ResponseData<SysRole> add(@RequestBody @Validated(BaseRequest.add.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.add(sysRoleRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除角色
     */
    @PostMapping(value = "/sysRole/delete")
    public ResponseData<?> delete(@RequestBody @Validated(BaseRequest.delete.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.del(sysRoleRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 批量删除角色
     */
    @PostMapping("/sysRole/batchDelete")
    public ResponseData<?> batchDelete(@RequestBody @Validated(BaseRequest.batchDelete.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.batchDelete(sysRoleRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑角色
     */
    @PostMapping("/sysRole/edit")
    public ResponseData<?> edit(@RequestBody @Validated(BaseRequest.edit.class) SysRoleRequest sysRoleRequest) {
        sysRoleService.edit(sysRoleRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看角色详情
     */
    @GetMapping("/sysRole/detail")
    public ResponseData<SysRole> detail(@Validated(BaseRequest.detail.class) SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData<>(sysRoleService.detail(sysRoleRequest));
    }

    /**
     * 分页查询-角色列表
     */
    @GetMapping("/sysRole/page")
    public ResponseData<PageResult<SysRole>> page(SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData<>(sysRoleService.findPage(sysRoleRequest));
    }

    /**
     * 获取所有角色列表
     * <p>一般用在用户分配角色，响应所有的角色列表</p>
     */
    @GetMapping("/sysRole/list")
    public ResponseData<List<SysRole>> list(SysRoleRequest sysRoleRequest) {
        return new SuccessResponseData<>(sysRoleService.userAssignRoleList(sysRoleRequest));
    }

}
