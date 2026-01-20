package cn.ibenbeni.bens.message.center.api.pojo.dto;

import cn.ibenbeni.bens.message.center.api.enums.core.MessageTaskStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息发送任务 DTO
 */
@Data
public class MessageSendTaskDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 消息模板ID
     */
    private Long templateId;

    /**
     * 消息模板编码
     */
    private String templateCode;

    /**
     * 目标用户总数
     */
    private Long totalUserCount;

    /**
     * 拆分后消息总数
     */
    private Long totalMsgCount;

    /**
     * 任务状态
     */
    private MessageTaskStatusEnum taskStatus;

    /**
     * 租户编号
     */
    private Long tenantId;

}
