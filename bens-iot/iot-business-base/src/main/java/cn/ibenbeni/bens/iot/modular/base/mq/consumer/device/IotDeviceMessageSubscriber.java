package cn.ibenbeni.bens.iot.modular.base.mq.consumer.device;

import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.iot.api.enums.device.IotDeviceStateEnum;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotDeviceService;
import cn.ibenbeni.bens.iot.modular.base.service.device.message.IotDeviceMessageService;
import cn.ibenbeni.bens.iot.modular.base.service.device.property.IotDevicePropertyService;
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
    private IotDevicePropertyService devicePropertyService;

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
            return;
        }

        // 指定租户执行
        TenantUtils.execute(message.getTenantId(), () -> {
            try {
                IotDeviceDO device = deviceService.validateDeviceExists(message.getDeviceId());

                // 更新设备连接的网关服务ID
                devicePropertyService.updateDeviceServerIdAsync(device.getDeviceId(), message.getServerId());

                // 强制设备上线
                forceDeviceOnline(message, device);

                // 处理消息
                deviceMessageService.handleUpstreamDeviceMessage(message, device);
            } catch (Exception ex) {
                log.error("[onMessage][消息处理错误][消息: {}][错误原因: {}]", message, ex.getMessage(), ex);
            }
        });
    }

    /**
     * 强制设备上线
     *
     * @param message 设备消息
     * @param device  设备
     */
    private void forceDeviceOnline(IotDeviceMessage message, IotDeviceDO device) {
        // 如果是已上线状态，则不处理
        if (ObjectUtil.equal(device.getStatusFlag(), IotDeviceStateEnum.ONLINE.getState())) {
            return;
        }
        // 若是 设备状态消息，则忽略，否则重复处理了
        if (ObjectUtil.equal(message.getMethod(), IotDeviceMessageMethodEnum.STATE_UPDATE.getMethod())) {
            return;
        }

        try {
            IotDeviceMessage onlineMessage = IotDeviceMessage.buildStateUpdateOnline();
            onlineMessage.setDeviceId(device.getDeviceId());

            deviceMessageService.sendDeviceMessage(onlineMessage);
        } catch (Exception ex) {
            // 注意：即使执行失败，也不影响主流程
            log.error("[forceDeviceOnline][message({}) deviceSn({}) 强制设备上线失败]", message, device.getDeviceSn(), ex);
        }
    }

}
