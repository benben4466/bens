package cn.ibenbeni.bens.message.center.modular.access.layer.access.action;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.message.center.modular.access.layer.access.model.MessageSendContext;
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
        log.info("[TemplateParseAction][开始检查模板][templateCode: {}]", context.getTemplateCode());
        // 两阶段拆分模式下，接入层仅做基础校验，变量解析下沉到拆分层或执行层
        // 这里可以保留对模板是否存在、渠道是否配置的校验，但跳过具体的 parseTemplate
        
        List<Integer> channels = context.getChannels();
        Long templateId = context.getTemplate().getTemplateId();
        
        // 遍历渠道校验
        for (Integer channelType : channels) {
            MessageTemplateContentDTO contentDTO = messageTemplateApi.getContentByTemplateAndChannel(templateId, channelType);
            if (contentDTO == null) {
                 log.warn("[TemplateParseAction][渠道({})无模板内容配置，跳过]", channelType);
                 // 是否需要中断？根据业务决定，这里暂时跳过，如果所有渠道都没配置，可能需要报错
                 continue;
            }
            // 可以在此将 contentDTO 放入 Context，供 TaskCreate 或 Splitter 使用（如果需要）
            // 目前 Splitter 会重新查库或从 Payload 拿，这里仅做 Check
        }

        log.info("[TemplateParseAction][模板检查完成]");
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

        return StrUtil.format(template, params);
    }

}
