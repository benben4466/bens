package cn.ibenbeni.bens.message.center.modular.execute.extension;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import cn.ibenbeni.bens.message.center.modular.execute.model.MessageHandleContext;
import cn.ibenbeni.bens.message.center.modular.execute.model.SendResult;

import java.util.Map;

/**
 * 消息渠道扩展点接口
 * 用于扩展新的消息发送渠道
 *
 * @author bens
 */
public interface MessageChannelExtension {

    /**
     * 获取支持的渠道类型
     *
     * @return 渠道类型枚举
     */
    MsgPushChannelTypeEnum getSupportedChannel();

    /**
     * 发送消息
     *
     * @param context 消息处理上下文
     * @return 发送结果
     */
    SendResult send(MessageHandleContext context);

    /**
     * 验证渠道配置是否有效
     *
     * @param config 渠道配置
     * @return true=有效, false=无效
     */
    default boolean validateConfig(Map<String, Object> config) {
        return true;
    }

    /**
     * 渠道是否可用
     *
     * @return true=可用, false=不可用
     */
    default boolean isAvailable() {
        return true;
    }

    /**
     * 获取渠道名称(用于日志)
     *
     * @return 渠道名称
     */
    default String getChannelName() {
        return getSupportedChannel() != null ? getSupportedChannel().getDesc() : "unknown";
    }

}