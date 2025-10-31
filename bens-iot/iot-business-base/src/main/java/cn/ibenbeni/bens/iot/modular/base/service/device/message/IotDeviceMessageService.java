package cn.ibenbeni.bens.iot.modular.base.service.device.message;

import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;

/**
 * 设备消息-服务
 */
public interface IotDeviceMessageService {

    /**
     * 初始化设备消息的 TDengine 超级表
     * <p>系统启动时，会自动初始化一次</p>
     */
    void defineDeviceMessageStable();

    /**
     * 发送设备消息
     *
     * @param message 设备消息
     * @return 设备消息
     */
    IotDeviceMessage sendDeviceMessage(IotDeviceMessage message);

    /**
     * 向设备发送消息
     *
     * @param message 设备消息
     * @param device  设备
     * @return 设备消息
     */
    IotDeviceMessage sendDeviceMessage(IotDeviceMessage message, IotDeviceDO device);

    /**
     * 处理设备上行的消息
     * <p>
     * 步骤：
     * 1.处理消息
     * 2.记录消息
     * 3.回复消息
     * </p>
     *
     * @param message 设备消息
     * @param device  设备
     */
    void handleUpstreamDeviceMessage(IotDeviceMessage message, IotDeviceDO device);

}
