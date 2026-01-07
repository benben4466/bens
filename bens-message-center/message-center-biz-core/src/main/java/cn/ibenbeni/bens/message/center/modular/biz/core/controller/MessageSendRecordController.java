package cn.ibenbeni.bens.message.center.modular.biz.core.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageSendRecordDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageSendRecordPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.response.MessageSendRecordResp;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageSendRecordService;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;

@Tag(name = "管理后台 - 消息发送记录")
@RestController
public class MessageSendRecordController {

    @Resource
    private MessageSendRecordService messageSendRecordService;

    @Operation(summary = "获取消息发送记录")
    @Parameter(name = "id", description = "记录ID", required = true, example = "10")
    @GetResource(path = "/msg-center/send-record/get")
    public ResponseData<MessageSendRecordResp> get(@RequestParam("id") Long id) {
        MessageSendRecordDO entity = messageSendRecordService.getById(id);
        return new SuccessResponseData<>(BeanUtil.toBean(entity, MessageSendRecordResp.class));
    }

    @Operation(summary = "获取消息发送记录分页列表")
    @GetResource(path = "/msg-center/send-record/page")
    public ResponseData<PageResult<MessageSendRecordResp>> page(@Valid MessageSendRecordPageReq req) {
        PageResult<MessageSendRecordDO> page = messageSendRecordService.page(req);
        return new SuccessResponseData<>(DbUtil.toBean(page, MessageSendRecordResp.class));
    }

}
