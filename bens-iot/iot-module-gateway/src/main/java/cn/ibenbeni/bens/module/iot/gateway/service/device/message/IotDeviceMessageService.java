package cn.ibenbeni.bens.module.iot.gateway.service.device.message;

import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;

/**
 * IOT-设备消息-服务
 * <p>网关</p>
 */
public interface IotDeviceMessageService {

    /**
     * 编码消息
     *
     * @param message    设备消息
     * @param productKey 产品Key
     * @param deviceSn   设备SN
     * @return 编码后的消息内容
     */
    byte[] encodeDeviceMessage(IotDeviceMessage message, String productKey, String deviceSn);

    /**
     * 解码消息
     *
     * @param bytes      消息内容
     * @param productKey 产品Key
     * @param deviceSn   设备SN
     * @return 解码后的设备消息
     */
    IotDeviceMessage decodeDeviceMessage(byte[] bytes, String productKey, String deviceSn);

    /**
     * 发送设备消息
     *
     * @param message    设备消息
     * @param productKey 产品Key
     * @param deviceSn   设备SN
     * @param serverId   服务ID(即设备连接的ServerId)
     */
    void sendDeviceMessage(IotDeviceMessage message, String productKey, String deviceSn, String serverId);

}
