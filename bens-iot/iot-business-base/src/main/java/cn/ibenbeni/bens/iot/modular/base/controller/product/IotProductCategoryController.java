package cn.ibenbeni.bens.iot.modular.base.controller.product;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.product.IotProductCategoryDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductCategoryPageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductCategorySaveReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.response.product.IotProductCategoryResp;
import cn.ibenbeni.bens.iot.modular.base.service.product.IotProductCategoryService;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.resource.api.annotation.PutResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

@Tag(name = "管理后台 - IOT产品分类")
@RestController
public class IotProductCategoryController {

    @Resource
    private IotProductCategoryService productCategoryService;

    @Operation(summary = "创建产品分类")
    @PostResource(path = "/iot/product-category/create")
    public ResponseData<Long> createProductCategory(@RequestBody @Valid IotProductCategorySaveReq saveReq) {
        return new SuccessResponseData<>(productCategoryService.createProductCategory(saveReq));
    }

    @Operation(summary = "删除产品分类")
    @Parameter(name = "id", description = "产品分类ID", required = true)
    @DeleteResource(path = "/iot/product-category/delete")
    public ResponseData<Boolean> deleteProductCategory(@RequestParam("id") Long id) {
        productCategoryService.deleteProductCategory(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除产品分类")
    @Parameter(name = "ids", description = "产品分类ID集合", required = true)
    @DeleteResource(path = "/iot/product-category/delete-list")
    public ResponseData<Boolean> deleteProductCategory(@RequestParam("ids") Set<Long> ids) {
        productCategoryService.deleteProductCategory(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新产品分类")
    @PutResource(path = "/iot/product-category/update")
    public ResponseData<Boolean> updateProductCategory(@RequestBody @Valid IotProductCategorySaveReq updateReq) {
        productCategoryService.updateProductCategory(updateReq);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取产品分类信息")
    @GetResource(path = "/iot/product-category/get")
    public ResponseData<IotProductCategoryResp> getProductCategory(@RequestParam("id") Long id) {
        IotProductCategoryDO productCategory = productCategoryService.getProductCategory(id);
        return new SuccessResponseData<>(BeanUtil.toBean(productCategory, IotProductCategoryResp.class));
    }

    @Operation(summary = "获取产品分类信息分页列表")
    @GetResource(path = "/iot/product-category/page")
    public ResponseData<PageResult<IotProductCategoryResp>> pageProductCategory(IotProductCategoryPageReq pageReq) {
        PageResult<IotProductCategoryDO> page = productCategoryService.pageProductCategory(pageReq);
        return new SuccessResponseData<>(DbUtil.toBean(page, IotProductCategoryResp.class));
    }

}
