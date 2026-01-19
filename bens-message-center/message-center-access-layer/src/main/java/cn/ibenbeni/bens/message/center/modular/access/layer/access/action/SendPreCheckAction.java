package cn.ibenbeni.bens.message.center.modular.access.layer.access.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.message.center.modular.access.layer.access.model.MessageSendContext;
import cn.ibenbeni.bens.message.center.api.MessageTemplateApi;
import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageTemplateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
    public void execute(MessageSendContext context) {
        log.info("[SendPreCheckAction][开始执行前置校验][templateCode: {}]", context.getTemplateCode());

        // 1. 校验模板编码不为空
        if (StrUtil.isBlank(context.getTemplateCode())) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_NOT_EXIST);
        }

        // 2. 获取并校验模板存在性
        MessageTemplateDTO template = messageTemplateApi.getByCode(context.getTemplateCode());
        if (template == null) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_NOT_EXIST);
        }

        // 3. 校验模板是否可用（启用、已审核）
        if (!messageTemplateApi.checkTemplateAvailable(context.getTemplateCode())) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_NOT_ENABLED);
        }

        // 4. 确定要发送的渠道列表
        List<Integer> channels = context.getChannels();
        if (CollUtil.isEmpty(channels)) {
            // 使用模板配置的渠道
            channels = template.getSupportChannels();
        }
        if (CollUtil.isEmpty(channels)) {
            throw new MessageCenterException(MessageCenterExceptionEnum.TEMPLATE_CHANNEL_NOT_SUPPORT);
        }

        // 5. 校验接收者信息
        if (MapUtil.isEmpty(context.getRecipient())) {
            throw new MessageCenterException(MessageCenterExceptionEnum.RECIPIENT_INFO_MISS);
        }

        // 6. 设置校验后的数据到上下文
        context.setTemplate(template);
        context.setChannels(channels);

        log.info("[SendPreCheckAction][前置校验通过][templateCode: {}, channels: {}]", context.getTemplateCode(), channels);
    }

    @Override
    public int getOrder() {
        return 100;
    }

}
