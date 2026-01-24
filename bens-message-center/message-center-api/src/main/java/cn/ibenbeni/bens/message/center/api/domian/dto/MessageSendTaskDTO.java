package cn.ibenbeni.bens.message.center.api.domian.dto;

import cn.ibenbeni.bens.message.center.api.enums.core.MessageTaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息发送任务 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
