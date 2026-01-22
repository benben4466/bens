package cn.ibenbeni.bens.message.center.modular.access.layer.access.service;

import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendRequest;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendResponse;

/**
 * 消息发送接入层服务接口
 */
public interface MessageSendAccessService {

    /**
     * 发送消息
     *
     * @param request 发送请求
     * @return 发送响应
     */
    MessageSendResponse send(MessageSendRequest request);

}
