package cn.ibenbeni.bens.module.iot.gateway.protocol.emqx.router;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.gateway.protocol.emqx.IotEmqxUpstreamProtocol;
import cn.ibenbeni.bens.module.iot.gateway.service.device.message.IotDeviceMessageService;
import cn.ibenbeni.bens.module.iot.gateway.service.device.message.IotDeviceMessageServiceImpl;
import io.vertx.mqtt.messages.MqttPublishMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * IOT-网关 EMQX 上行-消息处理器
 */
@Slf4j
public class IotEmqxUpstreamHandler {

    private final String serverId;

    private final IotDeviceMessageService deviceMessageService;

    public IotEmqxUpstreamHandler(IotEmqxUpstreamProtocol protocol) {
        this.serverId = protocol.getServerId();
        this.deviceMessageService = SpringUtil.getBean(IotDeviceMessageServiceImpl.GATEWAY_DEVICE_MESSAGE_SERVICE_NAME, IotDeviceMessageService.class);
    }

    /**
     * 处理 MQTT 消息
     *
     * @param mqttMessage MQTT 消息
     */
    public void handle(MqttPublishMessage mqttMessage) {
        log.info("[handle][收到 MQTT 消息, topic: {}, payload: {}]", mqttMessage.topicName(), mqttMessage.payload());
        String topic = mqttMessage.topicName();
        byte[] payload = mqttMessage.payload().getBytes();

        try {
            // 1.解析主题并校验主题规范
            String[] topicParts = topic.split(StrPool.SLASH);
            if (topicParts.length < 4 || StrUtil.hasBlank(topicParts[2], topicParts[3])) {
                log.warn("[handle][topic({}) 格式不正确，无法解析有效的 productKey 和 deviceSn]", topic);
                return;
            }
            String productKey = topicParts[2]; // 产品Key
            String deviceSn = topicParts[3];   // 设备SN

            // 2.解码消息
            IotDeviceMessage message = deviceMessageService.decodeDeviceMessage(payload, productKey, deviceSn);
            if (message == null) {
                log.warn("[handle][topic({}) payload({}) 消息解码失败]", topic, new String(payload));
                return;
            }

            // 3.投递到消息队列中
            deviceMessageService.sendDeviceMessage(message, productKey, deviceSn, serverId);
        } catch (Exception ex) {
            log.error("[handle][topic({}) payload({}) 处理异常]", topic, new String(payload), ex);
        }
    }

}
