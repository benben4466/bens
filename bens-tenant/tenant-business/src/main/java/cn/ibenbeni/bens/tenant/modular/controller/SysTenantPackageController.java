package cn.ibenbeni.bens.tenant.modular.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.tenant.modular.entity.SysTenantPackage;
import cn.ibenbeni.bens.tenant.modular.pojo.request.SysTenantPackageRequest;
import cn.ibenbeni.bens.tenant.modular.service.SysTenantPackageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 租户套餐控制器
 *
 * @author: benben
 * @time: 2025/6/30 下午5:29
 */
@RestController
public class SysTenantPackageController {

    @Resource
    private SysTenantPackageService sysTenantPackageService;

    /**
     * 新增租户套餐
     */
    @PostMapping("/tenantPackage/add")
    public ResponseData<SysTenantPackage> add(@RequestBody @Validated SysTenantPackageRequest sysTenantPackageRequest) {
        sysTenantPackageService.add(sysTenantPackageRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除租户套餐
     */
    @PostMapping("/tenantPackage/delete")
    public ResponseData<?> delete(@RequestBody @Validated(BaseRequest.delete.class) SysTenantPackageRequest sysTenantPackageRequest) {
        sysTenantPackageService.del(sysTenantPackageRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除租户套餐
     */
    @PostMapping("/tenantPackage/batchDelete")
    public ResponseData<?> batchDelete(@RequestBody @Validated(BaseRequest.batchDelete.class) SysTenantPackageRequest sysTenantPackageRequest) {
        sysTenantPackageService.batchDelete(sysTenantPackageRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑租户套餐
     */
    @PostMapping("/tenantPackage/edit")
    public ResponseData<?> edit(@RequestBody @Validated SysTenantPackageRequest sysTenantPackageRequest) {
        sysTenantPackageService.edit(sysTenantPackageRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 根据ID查询租户套餐
     */
    @GetMapping("/tenantPackage/detail")
    public ResponseData<SysTenantPackage> detail(@Validated SysTenantPackageRequest sysTenantPackageRequest) {
        return new SuccessResponseData<>(sysTenantPackageService.detail(sysTenantPackageRequest));
    }

    /**
     * 查询租户套餐列表
     */
    @GetMapping("/tenantPackage/list")
    public ResponseData<List<SysTenantPackage>> list(SysTenantPackageRequest sysTenantPackageRequest) {
        return new SuccessResponseData<>(sysTenantPackageService.findList(sysTenantPackageRequest));
    }

    /**
     * 查询租户套餐列表（分页）
     */
    @GetMapping("/tenantPackage/page")
    public ResponseData<PageResult<SysTenantPackage>> page(SysTenantPackageRequest sysTenantPackageRequest) {
        return new SuccessResponseData<>(sysTenantPackageService.findPage(sysTenantPackageRequest));
    }

}
