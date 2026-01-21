package cn.ibenbeni.bens.message.center.modular.execute.sender;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.message.center.api.MessageChannelConfigApi;
import cn.ibenbeni.bens.message.center.api.core.model.channelconfig.EmailMessageChannelConfigSpecs;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageChannelConfigDTO;
import cn.ibenbeni.bens.message.center.api.util.ChannelConfigUtils;
import cn.ibenbeni.bens.message.center.common.config.MessageCenterProperties;
import cn.ibenbeni.bens.message.center.modular.execute.model.MessageHandleContext;
import cn.ibenbeni.bens.message.center.modular.execute.model.SendResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.dromara.email.api.MailClient;
import org.dromara.email.comm.config.MailSmtpConfig;
import org.dromara.email.comm.entity.MailMessage;
import org.dromara.email.core.factory.MailFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 邮件发送器
 * 使用 SMS4J 库实现邮件发送
 */
@Slf4j
@Component
public class EmailMessageSender implements MessageChannelSender {

    @Resource
    private MessageCenterProperties properties;

    @Resource
    private MessageChannelConfigApi channelConfigApi;

    @Override
    public MsgPushChannelTypeEnum getSupportedChannel() {
        return MsgPushChannelTypeEnum.EMAIL;
    }

    @Override
    public SendResult send(MessageHandleContext context) {
        log.info("[EmailMessageSender][开始发送邮件][recordId: {}, recipient: {}]", context.getRecordId(), context.getRecipient());

        if (!preCheck(context)) {
            return SendResult.fail("SEND_PARAM_CHECK_ERROR", "参数校验失败");
        }

        // 渠道配置信息（账号配置信息）
        List<MessageChannelConfigDTO> channelConfigs = channelConfigApi.listByTemplateContentId(context.getTemplateContentId());
        List<EmailMessageChannelConfigSpecs> channelAccountConfigs = channelConfigs.stream()
                .map(channelConfig -> ChannelConfigUtils.convert(channelConfig.getChannelType(), channelConfig.getAccountConfig()))
                .filter(each -> Objects.nonNull(each) && each instanceof EmailMessageChannelConfigSpecs) // 确保不为空 且 类型正确
                .map(EmailMessageChannelConfigSpecs.class::cast)
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(channelAccountConfigs)) {
            return SendResult.fail("SEND_CHANNEL_CONFIG_EMPTY", "发送渠道为空");
        }

        for (EmailMessageChannelConfigSpecs specs : channelAccountConfigs) {
            MailSmtpConfig mailSmtpConfig = MailSmtpConfig.builder()
                    .smtpServer(specs.getSmtpServerAddress()) // 邮件服务器地址
                    .port(String.valueOf(specs.getSendPort())) // 端口号
                    .fromAddress(specs.getSenderEmail()) // 发件人邮箱
                    .username(specs.getSenderUsername()) // 发件人邮箱
                    .password(specs.getSenderPassword()) // 发件人密码(或授权码)
                    .isSSL(specs.getIsSSL() ? "true" : "false") // 是否开启 SSL
                    .isAuth(specs.getIsAuth() ? "true" : "false") // 是否开启验证
                    .retryInterval(specs.getRetryInterval()) // 重试间隔（单位：秒），默认为 5 秒
                    .maxRetries(specs.getMaxRetryCount()) // 重试次数，默认为 1 次
                    .build();
            MailFactory.put(getMailSmtpStorageKey(context.getTenantId(), specs.getSenderEmail()), mailSmtpConfig);
        }

        try {
            // 获取接收者邮箱
            Map<String, Object> recipient = context.getRecipient();
            // TODO [优化] 接收人类型
            String email = (String) recipient.get("email");

            if (email == null || email.isEmpty()) {
                return SendResult.fail("RECIPIENT_EMPTY", "接收者邮箱为空");
            }

            List<String> senderEmails = channelAccountConfigs.stream()
                    .map(EmailMessageChannelConfigSpecs::getSenderEmail)
                    .collect(Collectors.toList());
            List<String> mailSmtpStorageKeys = getMailSmtpStorageKeys(context.getTenantId(), senderEmails);
            List<MailClient> mailClients = mailSmtpStorageKeys.stream()
                    .map(MailFactory::createMailClient)
                    .collect(Collectors.toList());
            if (CollUtil.isEmpty(mailClients)) {
                return SendResult.fail("SEND_MAIL_EMPTY_ERROR", "邮箱客户端为空");
            }
            MailClient mailClient = mailClients.get(0);

            // 邮件消息
            ArrayList<String> recipientEmail = new ArrayList<>();
            recipientEmail.add(email);

            MailMessage mailMessage = MailMessage.Builder()
                    .mailAddress(recipientEmail) // 收件人邮箱集合
                    .title(context.getMessageTitle()) // 邮件标题
                    .body(context.getMessageContent()) // 邮件正文
                    .build();

            try {
                mailClient.send(mailMessage);
            } catch (Exception ex) {
                log.error("[send][忽略处理][发送邮件失败][入参: {} ,失败原因: {}]", JSON.toJSONString(context), ex.getMessage(), ex);
                return SendResult.fail("SEND_FAILED", "发送邮件失败");
            }

            // 默认发送成功
            return SendResult.success(String.valueOf(System.currentTimeMillis()), "邮件发送成功");
        } catch (Exception ex) {
            log.error("[EmailMessageSender][邮件发送异常][recordId: {}]", context.getRecordId(), ex);
            return SendResult.fail("SEND_ERROR", ex.getMessage());
        }
    }

    @Override
    public boolean isAvailable() {
        return properties.getChannels().getEmail().isEnabled();
    }

    /**
     * 前置检查
     *
     * @param context 上下文
     * @return true=通过；false=失败；
     */
    private boolean preCheck(MessageHandleContext context) {
        if (context == null) {
            log.error("[preCheck][忽略处理][上下文为空][入参: {}]", JSON.toJSONString(context));
            return false;
        }

        if (context.getTenantId() == null) {
            log.error("[preCheck][忽略处理][租户ID为空][入参: {}]", JSON.toJSONString(context));
            return false;
        }

        if (context.getTemplateContentId() == null) {
            log.error("[preCheck][忽略处理][消息模板内容ID为空][入参: {}]", JSON.toJSONString(context));
            return false;
        }

        return true;
    }

    /**
     * 获取邮件 Smtp 的存储 Key
     *
     * @param tenantId    租户 ID
     * @param senderEmail 发件人邮箱
     * @return 租户ID:邮箱号
     */
    private String getMailSmtpStorageKey(Long tenantId, String senderEmail) {
        if (tenantId == null || StrUtil.isBlank(senderEmail)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(tenantId).append(StrPool.COLON).append(senderEmail);
        return sb.toString();
    }

    /**
     * 获取邮件 Smtp 的存储 Keys
     *
     * @param tenantId     租户 ID
     * @param senderEmails 发件人邮箱集合
     */
    private List<String> getMailSmtpStorageKeys(Long tenantId, List<String> senderEmails) {
        if (tenantId == null || CollUtil.isEmpty(senderEmails)) {
            return null;
        }
        return senderEmails.stream()
                .map(senderEmail -> getMailSmtpStorageKey(tenantId, senderEmail))
                .collect(Collectors.toList());
    }

}
