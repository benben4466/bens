package cn.ibenbeni.bens.message.center.api.domian.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * RocketMQ 消息体
 */
@Data
public class MessageQueuePayload implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发送记录ID
     */
    private Long recordId;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 渠道类型
     */
    private Integer channelType;

    /**
     * 接收者信息
     */
    private Map<String, Object> recipient;

    /**
     * 接收者类型
     */
    private Integer recipientType;

    /**
     * 消息标题（已解析）
     */
    private String messageTitle;

    /**
     * 消息内容（已解析）
     */
    private String messageContent;

    /**
     * 原始模板变量
     */
    private Map<String, Object> msgVariables;

    /**
     * 渠道配置
     */
    private Map<String, Object> channelConfig;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 发送时间戳
     */
    private Long sendTime;

    /**
     * 租户ID
     */
    private Long tenantId;

}
