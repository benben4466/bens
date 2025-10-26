package cn.ibenbeni.bens.module.iot.core.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import cn.ibenbeni.bens.module.iot.core.enums.IotDeviceMessageMethodEnum;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;

import java.util.Map;

/**
 * IOT-设备消息工具类
 */
public class IotDeviceMessageUtils {

    /**
     * 生成消息ID
     */
    public static String generateMsgId() {
        return IdUtil.fastSimpleUUID();
    }

    /**
     * 是否是回复消息，通过 {@link IotDeviceMessage#getCode()} 非空进行识别
     *
     * @param message 消息
     * @return 是否
     */
    public static boolean isReplyMessage(IotDeviceMessage message) {
        return message.getCode() != null;
    }

    /**
     * 是否是上行消息：由设备发送
     *
     * @param message 消息
     * @return 是否
     */
    @SuppressWarnings("SimplifiableConditionalExpression")
    public static boolean isUpstreamMessage(IotDeviceMessage message) {
        IotDeviceMessageMethodEnum methodEnum = IotDeviceMessageMethodEnum.of(message.getMethod());
        Assert.notNull(methodEnum, "无法识别的消息方法：" + message.getMethod());
        // 注意：回复消息时，需要取反
        return !isReplyMessage(message) ? methodEnum.getUpstream() : !methodEnum.getUpstream();
    }

    /**
     * 获取标识符
     *
     * @param message 消息
     * @return 标识符
     */
    @SuppressWarnings("unchecked")
    public static String getIdentifier(IotDeviceMessage message) {
        if (message.getParams() == null) {
            return null;
        }

        if (StrUtil.equalsAny(message.getMethod(), IotDeviceMessageMethodEnum.EVENT_POST.getMethod(), IotDeviceMessageMethodEnum.SERVICE_INVOKE.getMethod())) {
            Map<String, Object> params = (Map<String, Object>) message.getParams();
            return MapUtil.getStr(params, "identifier");
        } else if (StrUtil.equalsAny(message.getMethod(), IotDeviceMessageMethodEnum.STATE_UPDATE.getMethod())) {
            Map<String, Object> params = (Map<String, Object>) message.getParams();
            return MapUtil.getStr(params, "state");
        }

        return null;
    }

    /**
     * 生成服务器编号
     *
     * @param serverPort 服务器端口
     * @return 服务器编号
     */
    public static String generateServerId(Integer serverPort) {
        String serverId = String.format("%s.%d", SystemUtil.getHostInfo().getAddress(), serverPort);
        // 避免一些场景无法使用 . 符号，例如说 RocketMQ Topic
        return serverId.replaceAll("\\.", "_");
    }

    /**
     * 构建消息总线网关设备消息的 Topic
     *
     * @param serverId 服务器编号
     * @return Topic
     */
    public static String buildMessageBusGatewayDeviceMessageTopic(String serverId) {
        return String.format(IotDeviceMessage.MESSAGE_BUS_GATEWAY_DEVICE_MESSAGE_TOPIC, serverId);
    }

}
