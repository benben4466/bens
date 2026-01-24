package cn.ibenbeni.bens.message.center.modular.access.layer.access.model;

import cn.ibenbeni.bens.common.chain.core.BaseChainContext;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageTemplateDTO;
import cn.ibenbeni.bens.message.center.api.domian.recipient.AbstractRecipientInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 用户发送消息上下文
 * 贯穿接入层 Action 链的核心数据载体
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserSendMessageContext extends BaseChainContext {

    /**
     * 业务ID
     * <p>回执ID</p>
     */
    private String bizId;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板参数
     */
    private Map<String, Object> templateParams;

    /**
     * 接收者信息
     */
    private List<AbstractRecipientInfo> recipientInfos;

    /**
     * 模板信息(校验后填充)
     */
    private MessageTemplateDTO template;

    /**
     * 发送任务ID (Batch ID)
     */
    private Long taskId;

}
