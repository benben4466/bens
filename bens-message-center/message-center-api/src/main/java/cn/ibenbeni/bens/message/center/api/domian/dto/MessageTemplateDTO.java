package cn.ibenbeni.bens.message.center.api.domian.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息模板信息传输对象
 */
@Data
public class MessageTemplateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板状态
     */
    private Integer templateStatus;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 支持渠道列表
     */
    private List<Integer> supportChannels;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 审核人ID
     */
    private Long auditUserId;

    /**
     * 审核意见
     */
    private String auditComment;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 消息模板内容列表
     */
    private List<MessageTemplateContentDTO> contentList;

}
