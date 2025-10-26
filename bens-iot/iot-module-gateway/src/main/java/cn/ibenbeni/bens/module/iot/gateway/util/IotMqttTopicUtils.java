package cn.ibenbeni.bens.module.iot.gateway.util;

import cn.hutool.core.util.StrUtil;

/**
 * IoT 网关 MQTT 主题工具类
 *
 * <p>用于统一管理 MQTT 协议中的主题常量，基于 Alink 协议规范</p>
 */
public class IotMqttTopicUtils {

    /**
     * 系统主题前缀
     */
    private static final String SYS_TOPIC_PREFIX = "/sys/";

    /**
     * 回复主题后缀
     */
    private static final String REPLY_TOPIC_SUFFIX = "_reply";

    /**
     * 根据消息方法构建对应的主题
     *
     * @param method 消息方法，例如 thing.property.post
     * @param productKey 产品 Key
     * @param deviceName 设备名称
     * @param isReply 是否为回复消息
     * @return 完整的主题路径
     */
    public static String buildTopicByMethod(String method, String productKey, String deviceName, boolean isReply) {
        if (StrUtil.isBlank(method)) {
            return null;
        }
        // 1. 将点分隔符转换为斜杠
        String topicSuffix = method.replace('.', '/');
        // 2. 对于回复消息，添加 _reply 后缀
        if (isReply) {
            topicSuffix += REPLY_TOPIC_SUFFIX;
        }
        // 3. 构建完整主题
        return SYS_TOPIC_PREFIX + productKey + "/" + deviceName + "/" + topicSuffix;
    }

}
