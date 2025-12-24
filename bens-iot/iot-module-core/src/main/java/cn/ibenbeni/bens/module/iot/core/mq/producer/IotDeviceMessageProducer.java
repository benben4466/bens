package cn.ibenbeni.bens.module.iot.core.mq.producer;

import cn.ibenbeni.bens.module.iot.core.messagebus.core.IotMessageBus;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * IoT-设备消息-生产者
 */
@Slf4j
@RequiredArgsConstructor
public class IotDeviceMessageProducer {

    private final IotMessageBus messageBus;

    /**
     * 发送设备消息
     *
     * @param message 设备消息
     */
    public void sendDeviceMessage(IotDeviceMessage message) {
        log.info("[sendDeviceMessage][发送设备消息({})]", message);
        messageBus.post(IotDeviceMessage.MESSAGE_BUS_DEVICE_MESSAGE_TOPIC, message);
    }

    /**
     * 发送网关设备消息
     *
     * @param serverId 网关的 ServerId 标识
     * @param message  设备消息
     */
    public void sendDeviceMessageToGateway(String serverId, IotDeviceMessage message) {
        log.info("[sendDeviceMessageToGateway][发送设备消息({}/{})]", serverId, message);
        messageBus.post(IotDeviceMessageUtils.buildMessageBusGatewayDeviceMessageTopic(serverId), message);
    }

}
