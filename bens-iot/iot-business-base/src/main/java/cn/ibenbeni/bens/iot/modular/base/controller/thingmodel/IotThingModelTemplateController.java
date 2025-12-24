package cn.ibenbeni.bens.iot.modular.base.controller.thingmodel;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.thingmodel.IotThingModelTemplateDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelTemplatePageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.thingmodel.IotThingModelTemplateSaveReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.response.thingmodel.IotThingModelTemplateResp;
import cn.ibenbeni.bens.iot.modular.base.service.thingmodel.IotThingModelTemplateService;
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

@Tag(name = "管理后台 - IOT物模型模板")
@RestController
public class IotThingModelTemplateController {

    @Resource
    private IotThingModelTemplateService thingModelTemplateService;

    @Operation(summary = "创建物模型模板")
    @PostResource(path = "/iot/thing-model-template/create")
    public ResponseData<Long> createThingModelTemplate(@RequestBody @Valid IotThingModelTemplateSaveReq saveReq) {
        return new SuccessResponseData<>(thingModelTemplateService.createThingModelTemplate(saveReq));
    }

    @Operation(summary = "删除物模型模板")
    @Parameter(name = "id", description = "物模型模板ID", required = true)
    @DeleteResource(path = "/iot/thing-model-template/delete")
    public ResponseData<Boolean> deleteThingModelTemplate(@RequestParam("id") Long id) {
        thingModelTemplateService.deleteThingModelTemplate(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除物模型模板")
    @Parameter(name = "ids", description = "物模型模板ID集合", required = true)
    @DeleteResource(path = "/iot/thing-model-template/delete-list")
    public ResponseData<Boolean> deleteThingModelTemplate(@RequestParam("ids") Set<Long> ids) {
        thingModelTemplateService.deleteThingModelTemplate(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新物模型模板")
    @PutResource(path = "/iot/thing-model-template/update")
    public ResponseData<Boolean> updateThingModelTemplate(@RequestBody @Valid IotThingModelTemplateSaveReq updateReq) {
        thingModelTemplateService.updateThingModelTemplate(updateReq);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取物模型模板")
    @GetResource(path = "/iot/thing-model-template/get")
    public ResponseData<IotThingModelTemplateResp> getThingModelTemplate(@RequestParam("id") Long id) {
        IotThingModelTemplateDO thingModelTemplate = thingModelTemplateService.getThingModelTemplate(id);
        return new SuccessResponseData<>(BeanUtil.toBean(thingModelTemplate, IotThingModelTemplateResp.class));
    }

    @Operation(summary = "获取物模型模板分页列表")
    @GetResource(path = "/iot/thing-model-template/page")
    public ResponseData<PageResult<IotThingModelTemplateResp>> pageThingModelTemplate(IotThingModelTemplatePageReq pageReq) {
        PageResult<IotThingModelTemplateDO> page = thingModelTemplateService.pageThingModelTemplate(pageReq);
        return new SuccessResponseData<>(DbUtil.toBean(page, IotThingModelTemplateResp.class));
    }

}
