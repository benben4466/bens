package cn.ibenbeni.bens.iot.modular.base.controller.thingmodel;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.thingmodel.IotThingModelDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelPageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelSaveReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.response.thingmodel.IotThingModelResp;
import cn.ibenbeni.bens.iot.modular.base.service.thingmodel.IotThingModelService;
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
import java.util.List;
import java.util.Set;

@Tag(name = "管理后台 - IOT物模型")
@RestController
public class IotThingModelController {

    @Resource
    private IotThingModelService thingModelService;

    @Operation(summary = "创建物模型")
    @PostResource(path = "/iot/thing-model/create")
    public ResponseData<Long> createThingModel(@RequestBody @Valid IotThingModelSaveReq saveReq) {
        return new SuccessResponseData<>(thingModelService.createThingModel(saveReq));
    }

    @Operation(summary = "批量创建物模型")
    @PostResource(path = "/iot/thing-model/create-list")
    public ResponseData<Boolean> createThingModelBatch(@RequestBody @Valid List<IotThingModelSaveReq> saveReqSet) {
        thingModelService.createBatchThingModel(saveReqSet);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "删除物模型")
    @Parameter(name = "id", description = "物模型ID", required = true)
    @DeleteResource(path = "/iot/thing-model/delete")
    public ResponseData<Boolean> deleteThingModel(@RequestParam("id") Long id) {
        thingModelService.deleteThingModel(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除物模型")
    @Parameter(name = "ids", description = "物模型ID集合", required = true)
    @DeleteResource(path = "/iot/thing-model/delete-list")
    public ResponseData<Boolean> deleteThingModel(@RequestParam("ids") Set<Long> ids) {
        thingModelService.deleteThingModel(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新物模型")
    @PutResource(path = "/iot/thing-model/update")
    public ResponseData<Boolean> updateThingModel(@RequestBody @Valid IotThingModelSaveReq updateReq) {
        thingModelService.updateThingModel(updateReq);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取物模型模板")
    @GetResource(path = "/iot/thing-model/get")
    public ResponseData<IotThingModelResp> getThingModel(@RequestParam("id") Long id) {
        IotThingModelDO thingModelTemplate = thingModelService.getThingModel(id);
        return new SuccessResponseData<>(BeanUtil.toBean(thingModelTemplate, IotThingModelResp.class));
    }

    @Operation(summary = "获取物模型模板分页列表")
    @GetResource(path = "/iot/thing-model/page")
    public ResponseData<PageResult<IotThingModelResp>> pageThingModelTemplate(IotThingModelPageReq pageReq) {
        PageResult<IotThingModelDO> page = thingModelService.pageThingModel(pageReq);
        return new SuccessResponseData<>(DbUtil.toBean(page, IotThingModelResp.class));
    }

}
