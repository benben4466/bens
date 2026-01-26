package cn.ibenbeni.bens.message.center.modular.execute.action;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.message.center.api.MessageTemplateApi;
import cn.ibenbeni.bens.message.center.api.domian.core.ParsedChannelContent;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendFailTypeEnum;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageTemplateContentDTO;
import cn.ibenbeni.bens.message.center.api.constants.chain.MessageCenterChainOrderConstants;
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
        log.info("[MessageContentParseAction][开始解析消息内容][业务ID: {}, 模板编码: {}]", context.getBizId(), context.getTemplateCode());

        try {
            // TODO [优化] 此方法是否 应该被写进 MessageTemplateCententApi 中
            // 1. 获取模板内容配置
            MessageTemplateContentDTO contentDTO = messageTemplateApi.getContentByTemplateCodeAndChannel(context.getTemplateCode(), context.getChannelType());

            if (contentDTO == null) {
                log.error("[MessageContentParseAction][未找到模板内容配置][业务ID: {}, 消息模板编码:{}, channelType: {}]", context.getBizId(), context.getTemplateCode(), context.getChannelType());
                context.setSuccess(false);
                context.setFailType(MsgSendFailTypeEnum.TEMPLATE_NOT_FOUND); // 或配置缺失
                context.setFailReason("未找到对应渠道的模板内容配置");
                return;
            }

            // 填充模板内容 ID
            context.setTemplateContentId(contentDTO.getId());

            // TODO 发送内容一致，是否可以缓存下来，不用每次都解析。同一渠道内容都一致，直接获取解析好的内容。
            // 填充解析内容
            context.setParsedContent(getParsedContent(contentDTO, context));

            log.info("[MessageContentParseAction][解析完成]");
        } catch (Exception ex) {
            log.error("[MessageContentParseAction][解析异常]", ex);
            context.setSuccess(false);
            context.setFailType(MsgSendFailTypeEnum.TEMPLATE_PARSE_FAIL);
            context.setFailReason("模板解析异常: " + ex.getMessage());
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

    /**
     * 获取解析内容
     *
     * @param content 消息模板内容
     * @param context 上下文
     * @return 解析内容
     */
    private ParsedChannelContent getParsedContent(MessageTemplateContentDTO content, MessageHandleContext context) {
        String parsedTitle = parseTemplate(content.getTitle(), context.getMsgVariables());
        String parsedContent = parseTemplate(content.getTemplateContent(), context.getMsgVariables());

        return ParsedChannelContent.builder()
                .title(parsedTitle)
                .mainBodyContent(parsedContent)
                .build();
    }

}
