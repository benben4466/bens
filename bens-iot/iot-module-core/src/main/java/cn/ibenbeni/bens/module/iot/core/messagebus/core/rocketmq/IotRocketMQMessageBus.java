package cn.ibenbeni.bens.module.iot.core.messagebus.core.rocketmq;

import cn.hutool.core.util.TypeUtil;
import cn.ibenbeni.bens.module.iot.core.messagebus.core.IotMessageBus;
import cn.ibenbeni.bens.module.iot.core.messagebus.core.IotMessageSubscriber;
import cn.ibenbeni.bens.rule.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

import javax.annotation.PreDestroy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class IotRocketMQMessageBus implements IotMessageBus {

    /**
     * RocketMQ客户端参数
     */
    private final RocketMQProperties rocketMQProperties;

    /**
     * RocketMQ消息发送核心模板
     */
    private final RocketMQTemplate rocketMQTemplate;

    /**
     * 主题对应的消费者映射
     * <p>DefaultMQPushConsumer：推模式 (Push) 消费者</p>
     */
    private final List<DefaultMQPushConsumer> topicConsumers = new ArrayList<>();

    @Override
    public void post(String topic, Object message) {
        SendResult result = rocketMQTemplate.syncSend(topic, JsonUtils.toJsonStr(message));
        log.info("[post][topic({}) 发送消息({}) result({})]", topic, message, result);
    }

    @SneakyThrows
    @Override
    public void register(IotMessageSubscriber<?> subscriber) {
        // 获取发布消息类型
        Type type = TypeUtil.getTypeArgument(subscriber.getClass(), 0);
        if (type == null) {
            throw new IllegalStateException(String.format("类型(%s) 需要设置消息类型", getClass().getName()));
        }

        // 1.创建默认Push模式消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        // 1.2 设置 NameServer
        consumer.setNamesrvAddr(rocketMQProperties.getNameServer());
        // 1.3 设置消费组
        consumer.setConsumerGroup(subscriber.getGroup());
        // 1.4 指定消费者监听的主题，默认订阅主题下所有标签
        consumer.subscribe(subscriber.getTopic(), "*");
        // 1.5 设置监听器
        // MessageListenerConcurrently（不保证消费顺序）：并发消息监听器.RocketMQ会自动回调这里的方法，每次有消息投递过来时触发。
        // messages 是一个批次的消息（通常是1条，也可能多条）。context：消费上下文（可以获取重试次数等信息）。
        consumer.setMessageListener((MessageListenerConcurrently) (messages, context) -> {
            for (MessageExt messageExt : messages) {
                try {
                    // 获取消息字节内容
                    byte[] body = messageExt.getBody();
                    // JsonUtils.parseObject：将字节数组内容转换成对象
                    // 调用订阅者消费消息
                    subscriber.onMessage(JsonUtils.parseObject(body, type));
                } catch (Exception ex) {
                    log.error("[onMessage][topic({}/{}) message({}) 消费者({}) 处理异常]", subscriber.getTopic(), subscriber.getGroup(), messageExt, subscriber.getClass().getName(), ex);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();

        // 2.缓存消费者
        topicConsumers.add(consumer);
    }

    /**
     * 销毁时，关闭所有消费者
     */
    @PreDestroy
    public void destroy() {
        for (DefaultMQPushConsumer consumer : topicConsumers) {
            try {
                consumer.shutdown();
                log.info("[destroy][关闭 group({}) 的消费者成功]", consumer.getConsumerGroup());
            } catch (Exception ex) {
                log.error("[destroy]关闭 group({}) 的消费者异常]", consumer.getConsumerGroup(), ex);
            }
        }
    }

}
