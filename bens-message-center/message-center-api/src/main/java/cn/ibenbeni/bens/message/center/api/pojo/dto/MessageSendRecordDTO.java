package cn.ibenbeni.bens.message.center.api.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 消息发送记录传输对象
 */
@Data
public class MessageSendRecordDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 关联模板ID
     */
    private Long templateId;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务关联ID
     */
    private String bizId;

    /**
     * 发送时的标题
     */
    private String msgTitle;

    /**
     * 模板参数变量
     */
    private Map<String, Object> msgVariables;

    /**
     * 渠道类型
     */
    private Integer channelType;

    /**
     * 接收者信息
     */
    private Map<String, Object> recipient;

    /**
     * 接收人类型
     */
    private Integer recipientType;

    /**
     * 发送状态
     */
    private Integer sendStatus;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 失败原因类型
     */
    private Integer failType;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 响应数据
     */
    private String responseData;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 租户ID
     */
    private Long tenantId;

}
