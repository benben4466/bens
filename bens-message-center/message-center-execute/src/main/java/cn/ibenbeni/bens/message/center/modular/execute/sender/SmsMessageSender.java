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
 * 短信发送器
 * 预留接口，后续接入具体短信服务商实现
 */
@Slf4j
@Component
public class SmsMessageSender implements MessageChannelSender {

    @Resource
    private MessageCenterProperties properties;

    @Override
    public MsgPushChannelTypeEnum getSupportedChannel() {
        return MsgPushChannelTypeEnum.SMS;
    }

    @Override
    public SendResult send(MessageHandleContext context) {
        log.info("[SmsMessageSender][开始发送短信][业务ID: {}]", context.getBizId());

        // 预留接口，当前记录日志并返回成功
        // TODO: 后续接入阿里云/腾讯云等短信服务商

        String phone = "1008611";

        if (phone == null || phone.isEmpty()) {
            return SendResult.fail("RECIPIENT_EMPTY", "接收者手机号为空");
        }

        log.info("[SmsMessageSender][短信发送（预留接口）][业务ID: {}, phone: {}]", context.getBizId(), phone);

        // 模拟发送成功
        return SendResult.success("sms-mock-" + System.currentTimeMillis(), "短信发送成功（预留接口）");
    }

    @Override
    public boolean isAvailable() {
        return properties.getChannels().getSms().isEnabled();
    }

}
