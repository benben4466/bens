package cn.ibenbeni.bens.iot.modular.base.controller.product;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.product.IotProductDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductPageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.product.IotProductSaveReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.response.product.IotProductResp;
import cn.ibenbeni.bens.iot.modular.base.service.product.IotProductService;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.resource.api.annotation.PutResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

@Tag(name = "管理后台 - IOT产品")
@RestController
public class IotProductController {

    @Resource
    private IotProductService productService;

    @Operation(summary = "创建产品")
    @PostResource(path = "/iot/product/create")
    public ResponseData<Long> createProduct(@RequestBody @Valid IotProductSaveReq saveReq) {
        return new SuccessResponseData<>(productService.createProduct(saveReq));
    }

    @Operation(summary = "删除产品")
    @Parameter(name = "id", description = "产品ID", required = true)
    @DeleteResource(path = "/iot/product/delete")
    public ResponseData<Boolean> deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除产品")
    @Parameter(name = "ids", description = "产品ID集合", required = true)
    @DeleteResource(path = "/iot/product/delete-list")
    public ResponseData<Boolean> deleteProduct(@RequestParam("ids") Set<Long> ids) {
        productService.deleteProduct(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新产品")
    @PutResource(path = "/iot/product/update")
    public ResponseData<Boolean> updateProduct(@RequestBody @Valid IotProductSaveReq updateReq) {
        productService.updateProduct(updateReq);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新产品状态")
    @Parameters({
            @Parameter(name = "id", description = "产品ID", required = true),
            @Parameter(name = "status", description = "产品状态", required = true)
    })
    @PutResource(path = "/iot/product/update-status")
    public ResponseData<Boolean> updateProductStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        productService.updateProductStatus(id, status);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取产品信息")
    @GetResource(path = "/iot/product/get")
    public ResponseData<IotProductResp> getProduct(@RequestParam("id") Long id) {
        IotProductDO iotProduct = productService.getProduct(id);
        return new SuccessResponseData<>(BeanUtil.toBean(iotProduct, IotProductResp.class));
    }

    @Operation(summary = "获取产品信息分页列表")
    @GetResource(path = "/iot/product/page")
    public ResponseData<PageResult<IotProductResp>> pageProductCategory(IotProductPageReq pageReq) {
        PageResult<IotProductDO> page = productService.pageProduct(pageReq);
        return new SuccessResponseData<>(DbUtil.toBean(page, IotProductResp.class));
    }

}
