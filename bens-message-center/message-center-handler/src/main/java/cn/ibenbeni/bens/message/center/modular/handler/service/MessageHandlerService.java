package cn.ibenbeni.bens.message.center.modular.handler.service;

import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageQueuePayload;

/**
 * 消息处理层服务接口
 */
public interface MessageHandlerService {

    /**
     * 处理MQ消息
     *
     * @param payload MQ消息体
     * @return 是否处理成功
     */
    boolean handleMessage(MessageQueuePayload payload);

}
