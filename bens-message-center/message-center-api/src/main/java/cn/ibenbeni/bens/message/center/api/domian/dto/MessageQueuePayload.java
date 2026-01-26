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
 * RocketMQ 消息体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageQueuePayload implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 渠道类型
     */
    private Integer channelType;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 原始模板变量
     */
    private Map<String, Object> msgVariables;

    /**
     * 接收者信息
     */
    private List<AbstractRecipientInfo> recipientInfos;

    /**
     * 消息发送明细
     */
    private Long msgSendDetailId;

    /**
     * 租户ID
     */
    private Long tenantId;

}
