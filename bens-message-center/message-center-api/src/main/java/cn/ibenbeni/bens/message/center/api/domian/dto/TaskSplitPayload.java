package cn.ibenbeni.bens.message.center.api.domian.dto;

import cn.ibenbeni.bens.message.center.api.domian.recipient.AbstractRecipientInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 任务拆分 MQ Payload
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskSplitPayload implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 任务ID
     */
    private Long taskId;

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
     * 模板详情（避免拆分层重复查库）
     */
    private MessageTemplateDTO template;

    /**
     * 租户ID
     */
    private Long tenantId;

}
