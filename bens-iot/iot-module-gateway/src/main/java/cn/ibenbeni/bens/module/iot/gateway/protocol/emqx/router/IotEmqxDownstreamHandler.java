package cn.ibenbeni.bens.module.iot.gateway.protocol.emqx.router;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.iot.api.IotDeviceCommonApi;
import cn.ibenbeni.bens.iot.api.pojo.dto.IotDeviceRespDTO;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import cn.ibenbeni.bens.module.iot.gateway.protocol.emqx.IotEmqxUpstreamProtocol;
import cn.ibenbeni.bens.module.iot.gateway.service.device.message.IotDeviceMessageService;
import cn.ibenbeni.bens.module.iot.gateway.service.device.message.IotDeviceMessageServiceImpl;
import cn.ibenbeni.bens.module.iot.gateway.util.IotMqttTopicUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * IoT-网关 EMQX 下行消息处理器
 * <p>从消息总线接收到下行消息，然后发布到 MQTT Broker，从而被设备所接收</p>
 */
@Slf4j
public class IotEmqxDownstreamHandler {

    private final IotEmqxUpstreamProtocol protocol;

    private final IotDeviceCommonApi deviceCommonApi;

    private final IotDeviceMessageService deviceMessageService;

    public IotEmqxDownstreamHandler(IotEmqxUpstreamProtocol protocol) {
        this.protocol = protocol;
        this.deviceCommonApi = SpringUtil.getBean(IotDeviceCommonApi.class);
        this.deviceMessageService = SpringUtil.getBean(IotDeviceMessageServiceImpl.GATEWAY_DEVICE_MESSAGE_SERVICE_NAME, IotDeviceMessageService.class);
    }

    /**
     * 处理下行消息
     *
     * @param message 设备消息
     */
    public void handle(IotDeviceMessage message) {
        // 1.获取设备信息
        IotDeviceRespDTO deviceInfo = deviceCommonApi.getDevice(message.getDeviceId());
        if (deviceInfo == null) {
            log.error("[handle][设备信息({})不存在]", message.getDeviceId());
            return;
        }

        // 2.根据方法构建主题
        String topic = buildTopicByMethod(message, deviceInfo.getProductKey(), deviceInfo.getDeviceName());
        if (StrUtil.isBlank(topic)) {
            log.warn("[handle][未知的消息方法: {}]", message.getMethod());
            return;
        }

        // 2.2编码消息
        byte[] payload = deviceMessageService.encodeDeviceMessage(message, deviceInfo.getProductKey(), deviceInfo.getDeviceSn());
        // 2.3发送消息
        protocol.publishMessage(topic, payload);
    }

    /**
     * 根据消息方法和回复状态构建主题
     *
     * @param message    设备消息
     * @param productKey 产品Key
     * @param deviceSn   设备SN
     * @return 构建的主题，如果方法不支持返回 null
     */
    private String buildTopicByMethod(IotDeviceMessage message, String productKey, String deviceSn) {
        // 1. 判断是否为回复消息
        boolean isReply = IotDeviceMessageUtils.isReplyMessage(message);
        // 2. 根据消息方法类型构建对应的主题
        return IotMqttTopicUtils.buildTopicByMethod(message.getMethod(), productKey, deviceSn, isReply);
    }

}
