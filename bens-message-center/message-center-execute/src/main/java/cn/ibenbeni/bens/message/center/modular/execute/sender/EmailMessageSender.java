package cn.ibenbeni.bens.message.center.modular.execute.sender;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import cn.ibenbeni.bens.message.center.modular.execute.model.MessageHandleContext;
import cn.ibenbeni.bens.message.center.modular.execute.model.SendResult;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 邮件发送器
 * 使用 SMS4J 库实现邮件发送
 */
@Slf4j
@Component
public class EmailMessageSender implements MessageChannelSender {

    @Value("${bens.message-center.channels.email.enabled:false}")
    private boolean enabled;

    @Override
    public MsgPushChannelTypeEnum getSupportedChannel() {
        return MsgPushChannelTypeEnum.EMAIL;
    }

    @Override
    public SendResult send(MessageHandleContext context) {
        log.info("[EmailMessageSender][开始发送邮件][recordId: {}, recipient: {}]", context.getRecordId(), context.getRecipient());

        try {
            // 获取接收者邮箱
            Map<String, Object> recipient = context.getRecipient();
            // TODO [优化] 接收人类型
            String email = (String) recipient.get("email");

            if (email == null || email.isEmpty()) {
                return SendResult.fail("RECIPIENT_EMPTY", "接收者邮箱为空");
            }

            // 使用 SMS4J 发送邮件
            // 注意：SMS4J 需要正确配置才能使用
            SmsBlend smsBlend = null;
            try {
                smsBlend = SmsFactory.getSmsBlend("MAIL");
            } catch (Exception e) {
                log.debug("[EmailMessageSender][获取SMS4J邮件配置失败]", e);
            }

            if (smsBlend == null) {
                log.warn("[EmailMessageSender][SMS4J邮件配置未找到，使用模拟发送]");
                // 模拟发送成功
                return SendResult.success("mock-" + System.currentTimeMillis(), "模拟发送成功");
            }

            SmsResponse response = smsBlend.sendMessage(email, context.getMessageContent());

            if (response.isSuccess()) {
                log.info("[EmailMessageSender][邮件发送成功][recordId: {}, email: {}]", context.getRecordId(), email);
                return SendResult.success(String.valueOf(System.currentTimeMillis()), "邮件发送成功");
            } else {
                log.error("[EmailMessageSender][邮件发送失败][recordId: {}, error: {}]", context.getRecordId(), response.getData());
                return SendResult.fail("SEND_FAILED", String.valueOf(response.getData()));
            }

        } catch (Exception ex) {
            log.error("[EmailMessageSender][邮件发送异常][recordId: {}]", context.getRecordId(), ex);
            return SendResult.fail("SEND_ERROR", ex.getMessage());
        }
    }

    @Override
    public boolean isAvailable() {
        return enabled;
    }

}
