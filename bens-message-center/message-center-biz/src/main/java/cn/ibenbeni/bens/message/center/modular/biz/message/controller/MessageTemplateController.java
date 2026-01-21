package cn.ibenbeni.bens.message.center.modular.biz.message.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageTemplateDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageTemplatePageReq;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageTemplateSaveReq;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.response.MessageTemplateResp;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageTemplateService;
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

@Tag(name = "管理后台 - 消息模板")
@RestController
public class MessageTemplateController {

    @Resource
    private MessageTemplateService messageTemplateService;

    @Operation(summary = "创建消息模板")
    @PostResource(path = "/msg-center/message-template/create")
    public ResponseData<Long> create(@RequestBody @Valid MessageTemplateSaveReq req) {
        return new SuccessResponseData<>(messageTemplateService.create(req));
    }

    @Operation(summary = "修改消息模板")
    @PutResource(path = "/msg-center/message-template/update")
    public ResponseData<Boolean> update(@RequestBody @Valid MessageTemplateSaveReq req) {
        messageTemplateService.updateById(req);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "删除消息模板")
    @Parameter(name = "id", description = "模板ID", required = true, example = "10")
    @DeleteResource(path = "/msg-center/message-template/delete")
    public ResponseData<Boolean> delete(@RequestParam("id") Long id) {
        messageTemplateService.deleteById(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "批量删除消息模板")
    @Parameter(name = "ids", description = "模板ID集合", required = true)
    @DeleteResource(path = "/msg-center/message-template/delete-list")
    public ResponseData<Boolean> deleteList(@RequestParam("ids") Set<Long> ids) {
        messageTemplateService.deleteByIds(ids);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获取消息模板")
    @Parameter(name = "id", description = "模板ID", required = true, example = "10")
    @GetResource(path = "/msg-center/message-template/get")
    public ResponseData<MessageTemplateResp> get(@RequestParam("id") Long id) {
        MessageTemplateDO entity = messageTemplateService.getById(id);
        return new SuccessResponseData<>(BeanUtil.toBean(entity, MessageTemplateResp.class));
    }

    @Operation(summary = "获取消息模板分页列表")
    @GetResource(path = "/msg-center/message-template/page")
    public ResponseData<PageResult<MessageTemplateResp>> page(@Valid MessageTemplatePageReq req) {
        PageResult<MessageTemplateDO> page = messageTemplateService.page(req);
        return new SuccessResponseData<>(DbUtil.toBean(page, MessageTemplateResp.class));
    }

}

