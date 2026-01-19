package cn.ibenbeni.bens.message.center.handler.sender;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import cn.ibenbeni.bens.message.center.handler.extension.MessageChannelExtension;
import cn.ibenbeni.bens.message.center.handler.model.MessageHandleContext;
import cn.ibenbeni.bens.message.center.handler.model.SendResult;

/**
 * 渠道发送器接口
 * 采用策略模式，每个渠道一个实现
 * 
 * 注意: 新接口请实现 MessageChannelExtension, 本接口保留用于兼容现有实现
 */
public interface MessageChannelSender extends MessageChannelExtension {

    /**
     * 返回支持的渠道类型
     *
     * @return 渠道类型
     */
    MsgPushChannelTypeEnum getSupportedChannel();

    /**
     * 执行发送
     *
     * @param context 消息处理上下文
     * @return 发送结果
     */
    SendResult send(MessageHandleContext context);

    /**
     * 渠道是否可用
     *
     * @return true=可用
     */
    default boolean isAvailable() {
        return true;
    }

}
