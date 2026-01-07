package cn.ibenbeni.bens.message.center.modular.biz.core.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageTemplateContentDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateContentPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateContentSaveReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.response.MessageTemplateContentResp;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageTemplateContentService;
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

@Tag(name = "管理后台 - 消息模板内容")
@RestController
public class MessageTemplateContentController {

    @Resource
    private MessageTemplateContentService messageTemplateContentService;

    @Operation(summary = "按模板ID获取内容列表")
    @Parameter(name = "templateId", description = "模板ID", required = true, example = "10")
    @GetResource(path = "/msg-center/message-template-content/list-by-template")
    public ResponseData<List<MessageTemplateContentResp>> listByTemplate(@RequestParam("templateId") Long templateId) {
        List<MessageTemplateContentDO> list = messageTemplateContentService.listByTemplateId(templateId);
        return new SuccessResponseData<>(BeanUtil.copyToList(list, MessageTemplateContentResp.class));
    }

}

