package cn.ibenbeni.bens.message.center.modular.execute.consumer;

import cn.ibenbeni.bens.message.center.api.domian.dto.MessageQueuePayload;
import cn.ibenbeni.bens.message.center.api.constants.mq.MessageCenterMqTopicConstants;
import cn.ibenbeni.bens.message.center.modular.execute.service.MessageHandlerService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * RocketMQ 消息消费者
 * 监听消息发送队列，处理消息发送任务
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = MessageCenterMqTopicConstants.EXECUTE_TOPIC,
        consumerGroup = MessageCenterMqTopicConstants.EXECUTE_CONSUMER_GROUP,
        selectorExpression = "*"
)
public class MessageConsumer implements RocketMQListener<String> {

    @Resource
    private MessageHandlerService messageHandlerService;

    @Override
    public void onMessage(String message) {
        log.info("[MessageConsumer][收到消息][message: {}]", message);

        try {
            // 1. 反序列化消息
            MessageQueuePayload payload = JSON.parseObject(message, MessageQueuePayload.class);

            if (payload == null || payload.getRecordId() == null) {
                log.error("[MessageConsumer][消息格式错误][message: {}]", message);
                return;
            }

            // 2. 处理消息
            boolean success = messageHandlerService.handleMessage(payload);

            if (!success) {
                log.warn("[MessageConsumer][消息处理失败，可能触发重试][recordId: {}]", payload.getRecordId());
            }

        } catch (Exception ex) {
            log.error("[MessageConsumer][消息处理异常]", ex);
            throw new RuntimeException("消息处理失败", ex);
        }
    }

}
