package cn.ibenbeni.bens.message.center.modular.access.layer.access.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.message.center.api.domian.recipient.AbstractRecipientInfo;
import cn.ibenbeni.bens.message.center.modular.access.layer.access.model.UserSendMessageContext;
import cn.ibenbeni.bens.message.center.api.MessageTemplateApi;
import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageTemplateDTO;
import cn.ibenbeni.bens.message.center.api.constants.chain.MessageCenterChainOrderConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 发送前置校验 Action
 * 负责校验模板存在性、状态、参数完整性等
 */
@Slf4j
@Component
public class SendPreCheckAction implements MessageSendAction {

    @Resource
    private MessageTemplateApi messageTemplateApi;

    @Override
    public void execute(UserSendMessageContext context) {
        log.info("[SendPreCheckAction][开始执行前置校验][templateCode: {}]", context.getTemplateCode());

        // 基础字段检查
        basicCheck(context);

        // 校验模板是否可用（启用、已审核）
        MessageTemplateDTO template = messageTemplateApi.checkTemplateAvailable(context.getTemplateCode());
        if (template == null) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_NOT_EXIST);
        }

        // 发送渠道检查
        sendChannelCheck(context);

        // 设置校验后的数据到上下文
        context.setTemplate(template);
        log.info("[SendPreCheckAction][前置校验通过][templateCode: {}]", context.getTemplateCode());
    }

    @Override
    public int getOrder() {
        return MessageCenterChainOrderConstants.AccessLayer.SEND_PRE_CHECK;
    }

    /**
     * 基础字段检查
     *
     * @param context 用户发送消息上下文
     */
    private void basicCheck(UserSendMessageContext context) {
        // 校验业务 ID
        if (context.getBizId() == null) {
            throw new MessageCenterException(MessageCenterExceptionEnum.BIZ_ID_MISS);
        }

        // 校验模板编码不为空
        if (StrUtil.isBlank(context.getTemplateCode())) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_NOT_EXIST);
        }

        // 接收者信息缺失
        if (CollUtil.isEmpty(context.getRecipientInfos())) {
            throw new MessageCenterException(MessageCenterExceptionEnum.RECIPIENT_INFO_MISS);
        }

        // 检查租户 ID
        if (context.getTenantId() == null) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TENANT_ID_MISS);
        }
    }

    /**
     * 发送渠道检查
     *
     * @param context 用户发送消息上下文
     */
    private void sendChannelCheck(UserSendMessageContext context) {
        // 获取待发送的渠道类型
        List<Integer> channelType = context.getRecipientInfos().stream()
                .filter(recipientInfo -> recipientInfo.getChannelType() != null && CollUtil.isNotEmpty(recipientInfo.getIdentifiers()))
                .map(AbstractRecipientInfo::getChannelType)
                .collect(Collectors.toList());

        // 检查待发送渠道类型与接受者渠道数量是否匹配
        if (channelType.size() != context.getRecipientInfos().size()) {
            throw new MessageCenterException(MessageCenterExceptionEnum.CHANNEL_TYPE_MISS);
        }

        // 检查模板是否支持渠道
        boolean isSupportChannel = messageTemplateApi.isSupportChannel(context.getTemplateCode(), new HashSet<>(channelType));
        if (!isSupportChannel) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_CHANNEL_NOT_SUPPORT);
        }
    }

}
