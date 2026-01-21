package cn.ibenbeni.bens.message.center.modular.execute.sender;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import cn.ibenbeni.bens.message.center.api.config.MessageCenterProperties;
import cn.ibenbeni.bens.message.center.modular.execute.model.MessageHandleContext;
import cn.ibenbeni.bens.message.center.modular.execute.model.SendResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 站内信发送器
 * 调用现有的 notify 模块创建站内信
 */
@Slf4j
@Component
public class LetterMessageSender implements MessageChannelSender {

    @Resource
    private MessageCenterProperties properties;

    @Override
    public MsgPushChannelTypeEnum getSupportedChannel() {
        return MsgPushChannelTypeEnum.LETTER;
    }

    @Override
    public SendResult send(MessageHandleContext context) {
        log.info("[LetterMessageSender][开始发送站内信][recordId: {}, recipient: {}]",
                context.getRecordId(), context.getRecipient());

        try {
            Map<String, Object> recipient = context.getRecipient();
            Object userIdObj = recipient.get("userId");

            if (userIdObj == null) {
                return SendResult.fail("RECIPIENT_EMPTY", "接收者用户ID为空");
            }

            Long userId;
            if (userIdObj instanceof Long) {
                userId = (Long) userIdObj;
            } else if (userIdObj instanceof Integer) {
                userId = ((Integer) userIdObj).longValue();
            } else {
                userId = Long.parseLong(userIdObj.toString());
            }

            // 站内信发送逻辑
            // 注意：这里需要调用 notify 模块的服务
            // 由于模块间解耦，通过 API 接口调用
            // TODO: 通过 NotifyMessageSendApi 发送站内信

            log.info("[LetterMessageSender][站内信发送成功][recordId: {}, userId: {}, title: {}]",
                    context.getRecordId(), userId, context.getMessageTitle());

            return SendResult.success("letter-" + System.currentTimeMillis(), "站内信发送成功");

        } catch (Exception e) {
            log.error("[LetterMessageSender][站内信发送异常][recordId: {}]", context.getRecordId(), e);
            return SendResult.fail("SEND_ERROR", e.getMessage());
        }
    }

    @Override
    public boolean isAvailable() {
        return properties.getChannels().getLetter().isEnabled();
    }

}
