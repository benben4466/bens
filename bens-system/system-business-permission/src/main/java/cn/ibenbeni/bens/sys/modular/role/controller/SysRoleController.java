package cn.ibenbeni.bens.sys.modular.role.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.easyexcel.util.ExcelUtils;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.resource.api.annotation.PutResource;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleDO;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RolePageReq;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleSaveReq;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleResp;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * 系统角色控制器
 *
 * @author benben
 * @date 2025/5/25  下午4:06
 */
@RestController
@Tag(name = "管理后台 - 角色")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Operation(summary = "创建角色")
    @PostResource(path = "/system/role/create")
    public ResponseData<Long> createRole(@Valid @RequestBody RoleSaveReq req) {
        return new SuccessResponseData<>(sysRoleService.createRole(req, null));
    }

    @Operation(summary = "删除角色")
    @Parameter(name = "id", description = "角色ID", example = "10", required = true)
    @DeleteResource(path = "/system/role/delete")
    public ResponseData<Boolean> deleteRole(@RequestParam("id") Long id) {
        sysRoleService.deleteRole(id);
        return new SuccessResponseData<>();
    }

    @Operation(summary = "批量删除角色")
    @Parameter(name = "ids", description = "角色ID列表", required = true)
    @DeleteResource(path = "/system/role/delete-list")
    public ResponseData<Boolean> deleteRole(@RequestParam("ids") Set<Long> ids) {
        sysRoleService.deleteRole(ids);
        return new SuccessResponseData<>();
    }

    @Operation(summary = "修改角色")
    @PutResource(path = "/system/role/update")
    public ResponseData<Boolean> updateRole(@Valid @RequestBody RoleSaveReq req) {
        sysRoleService.updateRole(req);
        return new SuccessResponseData<>();
    }

    @Operation(summary = "获得角色信息")
    @Parameter(name = "id", description = "角色ID", example = "10", required = true)
    @GetResource(path = "/system/role/get")
    public ResponseData<RoleResp> getRole(@RequestParam("id") Long id) {
        SysRoleDO role = sysRoleService.getRole(id);
        return new SuccessResponseData<>(BeanUtil.toBean(role, RoleResp.class));
    }

    @Operation(summary = "获取角色精简信息列表", description = "只包含被开启的角色，主要用于前端的下拉选项")
    @GetResource(path = "/system/role/simple-list")
    public ResponseData<List<RoleResp>> getSimpleRoleList() {
        List<SysRoleDO> list = sysRoleService.getRoleListByStatus(CollUtil.set(false, StatusEnum.ENABLE.getCode()));
        list.sort(Comparator.comparing(SysRoleDO::getRoleSort));
        return new SuccessResponseData<>(BeanUtils.toBean(list, RoleResp.class));
    }

    @Operation(summary = "获得角色分页")
    @GetResource(path = "/system/role/page")
    public ResponseData<PageResult<RoleResp>> getRolePage(RolePageReq req) {
        PageResult<SysRoleDO> pageResult = sysRoleService.getRolePage(req);
        return new SuccessResponseData<>(DbUtil.toBean(pageResult, RoleResp.class));
    }

    @Operation(summary = "导出角色 Excel")
    @GetResource(path = "/system/role/export-excel")
    public void export(HttpServletResponse response, @Validated RolePageReq pageReq) throws IOException {
        pageReq.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SysRoleDO> list = sysRoleService.getRolePage(pageReq).getRows();
        ExcelUtils.write(response, "角色数据.xls", "数据", RoleResp.class, BeanUtils.toBean(list, RoleResp.class));
    }

}
