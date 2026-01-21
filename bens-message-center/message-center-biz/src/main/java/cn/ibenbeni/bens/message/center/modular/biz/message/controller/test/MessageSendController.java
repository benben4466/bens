package cn.ibenbeni.bens.message.center.modular.biz.message.controller.test;

import cn.ibenbeni.bens.message.center.api.MessageSendApi;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendRequest;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendResponse;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Tag(name = "管理后台 - 消息发送(测试)")
@RestController
public class MessageSendController {

    @Resource
    private MessageSendApi messageSendApi;

    @Operation(summary = "模拟发送消息")
    @PostResource(path = "/msg-center/message/send/test")
    // @PostMapping("/msg-center/message/send/test")
    public MessageSendResponse send(@RequestBody @Valid MessageSendRequest request) {
        return messageSendApi.send(request);
    }

}
