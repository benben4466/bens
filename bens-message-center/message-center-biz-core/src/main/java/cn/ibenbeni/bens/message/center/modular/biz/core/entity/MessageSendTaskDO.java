package cn.ibenbeni.bens.message.center.modular.biz.core.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.message.center.api.enums.core.MessageTaskStatusEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息发送任务表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "message_send_task", autoResultMap = true)
public class MessageSendTaskDO extends BaseBusinessEntity {

    /**
     * 任务ID
     */
    @TableId(value = "task_id", type = IdType.ASSIGN_ID)
    private Long taskId;

    /**
     * 任务编码
     */
    @TableField("task_code")
    private String taskCode;

    /**
     * 消息模板ID
     */
    @TableField("template_id")
    private Long templateId;

    /**
     * 消息模板编码
     */
    @TableField("template_code")
    private String templateCode;

    /**
     * 目标用户总数
     */
    @TableField("total_user_count")
    private Long totalUserCount;

    /**
     * 拆分后消息总数(用户数*渠道数)
     */
    @TableField("total_msg_count")
    private Long totalMsgCount;

    /**
     * 任务状态
     */
    @TableField("task_status")
    private MessageTaskStatusEnum taskStatus;

    /**
     * 租户编号
     */
    @TableField("tenant_id")
    private Long tenantId;

}
