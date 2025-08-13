package cn.ibenbeni.bens.tenant.modular.controller;

import cn.hutool.core.bean.BeanUtil;
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
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.tenant.modular.entity.TenantDO;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantPageReq;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantSaveReq;
import cn.ibenbeni.bens.tenant.modular.pojo.response.TenantResp;
import cn.ibenbeni.bens.tenant.modular.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Tag(name = "管理后台 - 租户")
@RestController
public class TenantController {

    @Resource
    private TenantService tenantService;

    @Operation(summary = "创建租户")
    @PostResource(path = "/system/tenant/create")
    public ResponseData<Long> createTenant(@RequestBody @Valid TenantSaveReq req) {
        return new SuccessResponseData<>(tenantService.createTenant(req));
    }

    @Operation(summary = "删除租户")
    @Parameter(name = "id", description = "租户ID", required = true, example = "10")
    @DeleteResource(path = "/system/tenant/delete")
    public ResponseData<Boolean> deleteTenant(@RequestParam("id") Long id) {
        tenantService.deleteTenant(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除租户")
    @Parameter(name = "ids", description = "租户ID集合", required = true, example = "10")
    @DeleteResource(path = "/system/tenant/delete-list")
    public ResponseData<Boolean> deleteTenant(@RequestParam("ids") Set<Long> ids) {
        tenantService.deleteTenant(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新租户")
    @PutResource(path = "/system/tenant/update")
    public ResponseData<Boolean> updateTenant(@RequestBody @Valid TenantSaveReq req) {
        tenantService.updateTenant(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获得租户")
    @Parameter(name = "id", description = "租户ID", required = true, example = "10")
    @GetResource(path = "/system/tenant/get")
    public ResponseData<TenantResp> getTenant(@RequestParam("id") Long id) {
        return new SuccessResponseData<>(BeanUtil.toBean(tenantService.getTenant(id), TenantResp.class));
    }

    @Operation(summary = "使用租户名称,获得租户编号", description = "登录界面,根据用户的租户名,获得租户编号")
    @Parameter(name = "name", description = "租户名称", example = "10", required = true)
    @GetResource(path = "/system/tenant/get-id-by-name", requiredLogin = false)
    public ResponseData<Long> getTenantIdByName(@RequestParam("name") String name) {
        TenantDO tenant = tenantService.getTenantByName(name);
        return new SuccessResponseData<>(tenant.getTenantId());
    }

    @Operation(summary = "使用域名,获得租户信息", description = "登录界面,根据用户的域名,获得租户信息")
    @Parameter(name = "website", description = "域名", required = true, example = "www.ibenbeni.cn")
    @GetResource(path = "/system/tenant/get-by-website", requiredLogin = false)
    public ResponseData<TenantResp> getTenantByWebsite(@RequestParam("website") String website) {
        TenantDO tenant = tenantService.getTenantByWebsite(website);
        return new SuccessResponseData<>(BeanUtil.toBean(tenant, TenantResp.class));
    }

    @Operation(summary = "获取租户精简信息列表", description = "只包含被开启的租户,用于【首页】功能的选择租户选项")
    @GetResource(path = "/system/tenant/simple-list", requiredLogin = false)
    public ResponseData<List<TenantResp>> getTenantSimpleList() {
        List<TenantDO> tenantList = tenantService.listTenantByStatusFlag(StatusEnum.ENABLE.getCode());
        List<TenantResp> list = CollectionUtils.convertList(tenantList, tenantDO -> TenantResp.builder()
                .tenantId(tenantDO.getTenantId())
                .tenantName(tenantDO.getTenantName())
                .build()
        );
        return new SuccessResponseData<>(list);
    }

    @Operation(summary = "获得租户分页")
    @GetResource(path = "/system/tenant/page")
    public ResponseData<PageResult<TenantResp>> getTenantPage(@Valid TenantPageReq req) {
        PageResult<TenantDO> tenantPage = tenantService.getTenantPage(req);
        return new SuccessResponseData<>(DbUtil.toBean(tenantPage, TenantResp.class));
    }

    @Operation(summary = "导出租户Excel")
    @GetResource(path = "/system/tenant/export-excel")
    public void exportTenantExcel(HttpServletResponse response, @Valid TenantPageReq req) throws IOException {
        req.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TenantDO> rows = tenantService.getTenantPage(req).getRows();
        ExcelUtils.write(
                response,
                "租户.xls",
                "数据",
                TenantResp.class,
                BeanUtils.toBean(rows, TenantResp.class)
        );
    }

}
