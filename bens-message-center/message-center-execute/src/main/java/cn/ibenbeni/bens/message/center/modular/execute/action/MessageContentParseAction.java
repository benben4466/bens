package cn.ibenbeni.bens.message.center.modular.execute.action;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.message.center.api.MessageTemplateApi;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendFailTypeEnum;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageTemplateContentDTO;
import cn.ibenbeni.bens.message.center.common.constants.chain.MessageCenterChainOrderConstants;
import cn.ibenbeni.bens.message.center.modular.execute.model.MessageHandleContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 消息内容解析 Action
 * 职责: 根据模板ID和渠道类型，获取模板内容，并替换变量
 */
@Slf4j
@Component
public class MessageContentParseAction implements MessageHandleAction {

    @Resource
    private MessageTemplateApi messageTemplateApi;

    @Override
    public void execute(MessageHandleContext context) {
        log.info("[MessageContentParseAction][开始解析消息内容][recordId: {}, templateId: {}]", context.getRecordId(), context.getTemplateId());

        try {
            // 1. 获取模板内容配置
            MessageTemplateContentDTO contentDTO = messageTemplateApi.getContentByTemplateAndChannel(
                    context.getTemplateId(),
                    context.getChannelType()
            );

            if (contentDTO == null) {
                log.error("[MessageContentParseAction][未找到模板内容配置][templateId: {}, channelType: {}]", 
                        context.getTemplateId(), context.getChannelType());
                context.setSuccess(false);
                context.setFailType(MsgSendFailTypeEnum.TEMPLATE_NOT_FOUND); // 或配置缺失
                context.setFailReason("未找到对应渠道的模板内容配置");
                return;
            }

            // 2. 解析标题和正文
            String title = parseTemplate(contentDTO.getTitle(), context.getMsgVariables());
            String content = parseTemplate(contentDTO.getTemplateContent(), context.getMsgVariables());

            // 3. 填充到上下文
            context.setMessageTitle(title);
            context.setMessageContent(content);
            
            // 将渠道特定配置也合并或设置到上下文，供后续 Dispatch 使用
            if (contentDTO.getChannelConfig() != null) {
                // 如果 payload 中没有特定配置，则使用模板配置
                if (context.getChannelConfig() == null) {
                    context.setChannelConfig(contentDTO.getChannelConfig());
                } else {
                    // 合并? 暂以 payload 为准
                }
            }

            log.info("[MessageContentParseAction][解析完成]");

        } catch (Exception e) {
            log.error("[MessageContentParseAction][解析异常]", e);
            context.setSuccess(false);
            context.setFailType(MsgSendFailTypeEnum.TEMPLATE_PARSE_FAIL);
            context.setFailReason("模板解析异常: " + e.getMessage());
        }
    }

    private String parseTemplate(String template, Map<String, Object> params) {
        if (StrUtil.isBlank(template)) {
            return "";
        }
        if (params == null || params.isEmpty()) {
            return template;
        }
        return StrUtil.format(template, params);
    }

    @Override
    public int getOrder() {
        return MessageCenterChainOrderConstants.ExecuteLayer.CONTENT_PARSE;
    }

}
