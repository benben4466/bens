package cn.ibenbeni.bens.module.iot.gateway.codec;

import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;

public interface IotDeviceMessageCodec {

    /**
     * 编码消息
     *
     * @param message 消息
     * @return 编码后的消息内容
     */
    byte[] encode(IotDeviceMessage message);

    /**
     * 解码消息
     *
     * @param bytes 消息内容
     * @return 解码后的消息内容
     */
    IotDeviceMessage decode(byte[] bytes);

    /**
     * @return 数据格式（编码器类型）
     */
    String type();

}
