package cn.ibenbeni.bens.message.center.api;

import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendRequest;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendResponse;

import javax.validation.Valid;

/**
 * 统一消息发送 API 接口
 */
public interface MessageSendApi {

    /**
     * 发送消息（支持多渠道）
     *
     * @param request 发送请求
     * @return 发送响应
     */
    MessageSendResponse send(@Valid MessageSendRequest request);

}
