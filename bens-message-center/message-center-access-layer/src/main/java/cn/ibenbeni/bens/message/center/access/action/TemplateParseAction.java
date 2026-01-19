package cn.ibenbeni.bens.message.center.access.action;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.message.center.access.model.MessageSendContext;
import cn.ibenbeni.bens.message.center.api.MessageTemplateApi;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageTemplateContentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 模板解析 Action
 * 负责将模板中的变量占位符替换为实际值，生成最终发送内容
 */
@Slf4j
@Component
public class TemplateParseAction implements MessageSendAction {

    @Resource
    private MessageTemplateApi messageTemplateApi;

    @Override
    public void execute(MessageSendContext context) {
        log.info("[TemplateParseAction][开始解析模板][templateCode: {}]", context.getTemplateCode());

        List<Integer> channels = context.getChannels();
        Long templateId = context.getTemplate().getTemplateId();
        Map<String, Object> params = context.getTemplateParams();

        // 遍历每个渠道，获取模板内容并进行变量替换
        for (Integer channelType : channels) {
            MessageTemplateContentDTO contentDTO = messageTemplateApi.getContentByTemplateAndChannel(templateId, channelType);
            if (contentDTO == null) {
                log.warn("[TemplateParseAction][渠道({})无模板内容配置，跳过]", channelType);
                continue;
            }

            // 解析标题
            String parsedTitle = parseTemplate(contentDTO.getTitle(), params);
            // 解析内容
            String parsedContent = parseTemplate(contentDTO.getTemplateContent(), params);

            // 存储解析结果
            MessageSendContext.ParsedContent parsed = new MessageSendContext.ParsedContent();
            parsed.setChannelType(channelType);
            parsed.setTitle(parsedTitle);
            parsed.setContent(parsedContent);
            parsed.setChannelConfig(contentDTO.getChannelConfig());

            context.getParsedContents().put(channelType, parsed);

            log.info("[TemplateParseAction][渠道({})模板解析完成][title: {}]", channelType, parsedTitle);
        }

        log.info("[TemplateParseAction][模板解析完成][解析渠道数: {}]", context.getParsedContents().size());
    }

    @Override
    public int getOrder() {
        return 200;
    }

    /**
     * 解析模板，替换变量占位符
     * 支持 {varName} 格式的占位符
     *
     * @param template 模板内容
     * @param params   参数
     * @return 解析后的内容
     */
    private String parseTemplate(String template, Map<String, Object> params) {
        if (StrUtil.isBlank(template)) {
            return template;
        }
        if (params == null || params.isEmpty()) {
            return template;
        }
        // 使用 Hutool 的 StrUtil.format 进行变量替换
        return StrUtil.format(template, params);
    }

}
