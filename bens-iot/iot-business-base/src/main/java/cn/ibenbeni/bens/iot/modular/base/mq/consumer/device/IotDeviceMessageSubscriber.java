package cn.ibenbeni.bens.iot.modular.base.mq.consumer.device;

import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotDeviceService;
import cn.ibenbeni.bens.iot.modular.base.service.device.message.IotDeviceMessageService;
import cn.ibenbeni.bens.module.iot.core.enums.IotDeviceMessageMethodEnum;
import cn.ibenbeni.bens.module.iot.core.messagebus.core.IotMessageBus;
import cn.ibenbeni.bens.module.iot.core.messagebus.core.IotMessageSubscriber;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import cn.ibenbeni.bens.tenant.api.util.TenantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 针对 {@link IotDeviceMessage} 的业务处理器：调用 method 对应的逻辑。
 * <p>
 * 例如说：
 * 1.{@link IotDeviceMessageMethodEnum#PROPERTY_POST} 属性上报时，记录设备属性
 * </p>
 */
@Slf4j
@Component
public class IotDeviceMessageSubscriber implements IotMessageSubscriber<IotDeviceMessage> {

    @Resource
    private IotMessageBus messageBus;

    @Resource
    private IotDeviceService deviceService;

    @Resource
    private IotDeviceMessageService deviceMessageService;

    @PostConstruct
    public void init() {
        messageBus.register(this);
    }

    @Override
    public String getTopic() {
        return IotDeviceMessage.MESSAGE_BUS_DEVICE_MESSAGE_TOPIC;
    }

    @Override
    public String getGroup() {
        return "iot_device_message_consumer";
    }

    @Override
    public void onMessage(IotDeviceMessage message) {
        // 只处理上行消息
        if (!IotDeviceMessageUtils.isUpstreamMessage(message)) {
            log.error("[onMessage][message({}) 非上行消息，不进行处理]", message);
        }

        // 指定租户执行
        TenantUtils.execute(message.getTenantId(), () -> {
            IotDeviceDO device = deviceService.validateDeviceExists(message.getDeviceId());

            // 处理消息
            deviceMessageService.handleUpstreamDeviceMessage(message, device);
        });
    }

}
