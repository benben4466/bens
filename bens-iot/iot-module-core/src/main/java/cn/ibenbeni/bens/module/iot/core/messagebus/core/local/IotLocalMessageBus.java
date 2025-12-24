package cn.ibenbeni.bens.module.iot.core.messagebus.core.local;

import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.module.iot.core.messagebus.core.IotMessageBus;
import cn.ibenbeni.bens.module.iot.core.messagebus.core.IotMessageSubscriber;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地消息总线
 * <p>注意：仅适用于单机场景！！！</p>
 */
@Slf4j
@RequiredArgsConstructor
public class IotLocalMessageBus implements IotMessageBus {

    private final ApplicationContext applicationContext;

    /**
     * 订阅者映射表
     * <p>Key=主题；Value=同主题的订阅者；</p>
     */
    private final Map<String, List<IotMessageSubscriber<?>>> subscribers = new HashMap<>();

    @Override
    public void post(String topic, Object message) {
        applicationContext.publishEvent(new IotLocalMessage(topic, message));
    }

    @Override
    public void register(IotMessageSubscriber<?> subscriber) {
        String topic = subscriber.getTopic();
        List<IotMessageSubscriber<?>> topicSubscribers = subscribers.computeIfAbsent(topic, k -> new ArrayList<>());
        topicSubscribers.add(subscriber);
        log.info("[IotMessageSubscriber-Register][topic({}/{}) 注册消费者({})成功]", topic, subscriber.getGroup(), subscriber.getClass().getName());
    }

    /**
     * 本地总线-设备消息监听
     *
     * @param message 设备消息
     */
    @EventListener
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void onMessage(IotLocalMessage message) {
        String topic = message.getTopic();
        List<IotMessageSubscriber<?>> topicSubscribers = subscribers.get(topic);
        if (CollUtil.isEmpty(topicSubscribers)) {
            log.info("[IotDeviceMessage-onMessage]设备消息无订阅者, 抛弃. 主题: {}, 消息内容: {}", topic, JSON.toJSONString(message.getMessage(), JSONWriter.Feature.WriteMapNullValue));
            return;
        }

        for (IotMessageSubscriber subscriber : topicSubscribers) {
            try {
                subscriber.onMessage(message.getMessage());
            } catch (Exception ex) {
                log.error("[IotDeviceMessage-onMessage][]topic({}/{}) message({}) 消费者({}) 处理异常", subscriber.getTopic(), subscriber.getGroup(), JSON.toJSONString(message.getMessage(), JSONWriter.Feature.WriteMapNullValue), subscriber.getClass().getName(), ex);
            }
        }
    }

}
