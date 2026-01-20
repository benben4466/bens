package cn.ibenbeni.bens.message.center.modular.execute.sender;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import cn.ibenbeni.bens.message.center.modular.execute.model.MessageHandleContext;
import cn.ibenbeni.bens.message.center.modular.execute.model.SendResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 推送发送器
 * 预留接口，后续接入极光/友盟等推送服务实现
 */
@Slf4j
@Component
public class PushMessageSender implements MessageChannelSender {

    @Value("${bens.message-center.channels.push.enabled:false}")
    private boolean enabled;

    @Override
    public MsgPushChannelTypeEnum getSupportedChannel() {
        return MsgPushChannelTypeEnum.PUSH;
    }

    @Override
    public SendResult send(MessageHandleContext context) {
        log.info("[PushMessageSender][开始发送推送][recordId: {}, recipient: {}]",
                context.getRecordId(), context.getRecipient());

        // 预留接口，当前记录日志并返回成功
        // TODO: 后续接入极光/友盟等推送服务

        Map<String, Object> recipient = context.getRecipient();
        String deviceToken = (String) recipient.get("deviceToken");

        if (deviceToken == null || deviceToken.isEmpty()) {
            return SendResult.fail("RECIPIENT_EMPTY", "设备Token为空");
        }

        log.info("[PushMessageSender][推送发送（预留接口）][recordId: {}, deviceToken: {}, title: {}, content: {}]",
                context.getRecordId(), deviceToken, context.getMessageTitle(), context.getMessageContent());

        // 模拟发送成功
        return SendResult.success("push-mock-" + System.currentTimeMillis(), "推送发送成功（预留接口）");
    }

    @Override
    public boolean isAvailable() {
        return enabled;
    }
}
