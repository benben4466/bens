package cn.ibenbeni.bens.tenant.modular.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.resource.api.annotation.PutResource;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import cn.ibenbeni.bens.tenant.modular.entity.TenantPackageDO;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantPackagePageReq;
import cn.ibenbeni.bens.tenant.modular.pojo.request.TenantPackageSaveReq;
import cn.ibenbeni.bens.tenant.modular.pojo.response.TenantPackageResp;
import cn.ibenbeni.bens.tenant.modular.pojo.response.TenantPackageSimpleResp;
import cn.ibenbeni.bens.tenant.modular.service.TenantPackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * 租户套餐控制器
 *
 * @author: benben
 * @time: 2025/6/30 下午5:29
 */
@Tag(name = "管理后台 - 租户套餐")
@RestController
public class TenantPackageController {

    @Resource
    private TenantPackageService tenantPackageService;

    @Operation(summary = "创建租户套餐")
    @PostResource(path = "/system/tenant-package/create")
    public ResponseData<Long> createTenantPackage(@RequestBody @Valid TenantPackageSaveReq req) {
        return new SuccessResponseData<>(tenantPackageService.createTenantPackage(req));
    }

    @Operation(summary = "删除租户套餐")
    @Parameter(name = "id", description = "租户套餐ID", required = true)
    @DeleteResource(path = "/system/tenant-package/delete")
    public ResponseData<Boolean> deleteTenantPackage(@RequestParam("id") Long id) {
        tenantPackageService.deleteTenantPackage(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "删除租户套餐")
    @Parameter(name = "ids", description = "租户套餐ID集合", required = true)
    @DeleteResource(path = "/system/tenant-package/delete-list")
    public ResponseData<Boolean> deleteTenantPackage(@RequestParam("ids") Set<Long> ids) {
        tenantPackageService.deleteTenantPackage(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新租户套餐")
    @PutResource(path = "/system/tenant-package/update")
    public ResponseData<Boolean> updateTenantPackage(@RequestBody @Valid TenantPackageSaveReq req) {
        tenantPackageService.updateTenantPackage(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获得租户套餐")
    @Parameter(name = "id", description = "租户套餐ID", required = true)
    @GetResource(path = "/system/tenant-package/get")
    public ResponseData<TenantPackageResp> getTenantPackage(@RequestParam("id") Long id) {
        TenantPackageDO tenantPackage = tenantPackageService.getTenantPackage(id);
        return new SuccessResponseData<>(BeanUtil.toBean(tenantPackage, TenantPackageResp.class));
    }

    @Operation(summary = "获得租户套餐分页")
    @GetResource(path = "/system/tenant-package/page")
    public ResponseData<PageResult<TenantPackageResp>> getTenantPackagePage(@Valid TenantPackagePageReq req) {
        PageResult<TenantPackageDO> tenantPackagePage = tenantPackageService.getTenantPackagePage(req);
        return new SuccessResponseData<>(DbUtil.toBean(tenantPackagePage, TenantPackageResp.class));
    }

    @Operation(summary = "获取租户套餐精简信息列表", description = "只包含被开启的租户套餐,主要用于前端的下拉选项")
    @GetResource(path = "/system/tenant-package/simple-list")
    public ResponseData<List<TenantPackageSimpleResp>> getTenantPackageList() {
        List<TenantPackageDO> tenantPackageList = tenantPackageService.listTenantPackageByStatusFlag(StatusEnum.ENABLE.getCode());
        return new SuccessResponseData<>(BeanUtils.toBean(tenantPackageList, TenantPackageSimpleResp.class));
    }

}
