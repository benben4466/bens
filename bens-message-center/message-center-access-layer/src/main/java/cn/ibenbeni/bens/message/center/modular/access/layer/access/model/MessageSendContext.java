package cn.ibenbeni.bens.message.center.modular.access.layer.access.model;

import cn.ibenbeni.bens.common.chain.core.BaseChainContext;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageTemplateDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息发送上下文
 * 贯穿接入层 Action 链的核心数据载体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageSendContext extends BaseChainContext {

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板参数
     */
    private Map<String, Object> templateParams;

    /**
     * 业务ID
     * <p>回执ID</p>
     */
    private String bizId;

    /**
     * 接收者类型
     */
    private Integer recipientType;

    /**
     * 接收者信息(userId、phone、email等)
     */
    private Map<String, Object> recipient;

    /**
     * 指定渠道列表(为空则使用模板配置)
     */
    private List<Integer> channels;

    /**
     * 模板信息(校验后填充)
     */
    private MessageTemplateDTO template;

    /**
     * 已解析的各渠道内容
     * key=channelType, value=解析后的内容
     */
    private Map<Integer, ParsedContent> parsedContents = new HashMap<>();

    /**
     * 发送记录ID列表
     */
    private List<Long> recordIds = new ArrayList<>();

    /**
     * 发送任务ID (Batch ID)
     */
    private Long taskId;

    /**
     * 解析后的内容
     */
    @Data
    public static class ParsedContent {
        /**
         * 渠道类型
         */
        private Integer channelType;

        /**
         * 解析后的标题
         */
        private String title;

        /**
         * 解析后的内容
         */
        private String content;

        /**
         * 渠道配置
         */
        private Map<String, Object> channelConfig;
    }

}
