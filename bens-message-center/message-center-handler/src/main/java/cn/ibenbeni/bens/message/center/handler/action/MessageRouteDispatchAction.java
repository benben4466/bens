package cn.ibenbeni.bens.message.center.handler.action;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendFailTypeEnum;
import cn.ibenbeni.bens.message.center.handler.extension.MessageChannelExtension;
import cn.ibenbeni.bens.message.center.handler.extension.MessageChannelManager;
import cn.ibenbeni.bens.message.center.handler.model.MessageHandleContext;
import cn.ibenbeni.bens.message.center.handler.model.SendResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息路由分发 Action
 * 使用 MessageChannelManager 自动发现和管理渠道
 */
@Slf4j
@Component
public class MessageRouteDispatchAction implements MessageHandleAction {

    @Resource
    private MessageChannelManager channelManager;

    @Override
    public void execute(MessageHandleContext context) {
        log.info("[MessageRouteDispatchAction][开始路由分发][recordId: {}, channelType: {}]",
                context.getRecordId(), context.getChannelType());

        // 转换渠道类型
        MsgPushChannelTypeEnum channelType;
        try {
            channelType = MsgPushChannelTypeEnum.fromCode(context.getChannelType());
        } catch (Exception e) {
            log.error("[MessageRouteDispatchAction][渠道类型无效][channelType: {}]", context.getChannelType());
            context.setSuccess(false);
            context.setFailType(MsgSendFailTypeEnum.CHANNEL_NOT_SUPPORT);
            context.setFailReason("无效的渠道类型: " + context.getChannelType());
            return;
        }

        // 1. 获取对应的渠道扩展
        MessageChannelExtension channelExtension = channelManager.getChannel(channelType);
        if (channelExtension == null) {
            log.error("[MessageRouteDispatchAction][未找到对应的渠道实现][channelType: {}]", channelType);
            context.setSuccess(false);
            context.setFailType(MsgSendFailTypeEnum.CHANNEL_NOT_SUPPORT);
            context.setFailReason("不支持的渠道类型: " + channelType.getDesc());
            return;
        }

        // 2. 检查渠道是否可用
        if (!channelExtension.isAvailable()) {
            log.warn("[MessageRouteDispatchAction][渠道不可用][channelType: {}]", channelType);
            context.setSuccess(false);
            context.setFailType(MsgSendFailTypeEnum.CHANNEL_NOT_AVAILABLE);
            context.setFailReason("渠道暂不可用: " + channelType.getDesc());
            return;
        }

        // 3. 执行发送
        try {
            log.debug("[MessageRouteDispatchAction][调用渠道发送][channel: {}, extension: {}]",
                    channelType, channelExtension.getClass().getSimpleName());

            SendResult result = channelExtension.send(context);

            if (result.isSuccess()) {
                context.setSuccess(true);
                context.setResponseData(result.getResponseData());
                log.info("[MessageRouteDispatchAction][发送成功][recordId: {}, channelMsgId: {}]",
                        context.getRecordId(), result.getChannelMsgId());
            } else {
                context.setSuccess(false);
                context.setFailType(MsgSendFailTypeEnum.CHANNEL_SEND_FAIL);
                context.setFailReason(result.getErrorMessage());
                log.error("[MessageRouteDispatchAction][发送失败][recordId: {}, error: {}]",
                        context.getRecordId(), result.getErrorMessage());
            }

        } catch (Exception e) {
            log.error("[MessageRouteDispatchAction][发送异常][recordId: {}]", context.getRecordId(), e);
            context.setSuccess(false);
            context.setFailType(MsgSendFailTypeEnum.CHANNEL_SEND_FAIL);
            context.setFailReason("发送异常: " + e.getMessage());
        }
    }

    @Override
    public int getOrder() {
        return 200;
    }
}
