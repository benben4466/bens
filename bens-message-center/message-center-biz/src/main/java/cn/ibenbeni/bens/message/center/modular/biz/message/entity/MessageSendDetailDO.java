package cn.ibenbeni.bens.message.center.modular.biz.message.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.message.center.api.enums.core.MessageDetailStatusEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Map;

/**
 * 消息发送执行明细表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "message_send_detail", autoResultMap = true)
public class MessageSendDetailDO extends BaseBusinessEntity {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 父任务ID
     */
    @TableField("task_id")
    private Long taskId;

    /**
     * 接收者标识(手机号/邮箱/OpenID)
     */
    @TableField("recipient_account")
    private String recipientAccount;

    /**
     * 渠道类型
     */
    @TableField("channel_type")
    private MsgPushChannelTypeEnum channelType;

    /**
     * 模板参数变量
     */
    @TableField(value = "msg_variables", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> msgVariables;

    /**
     * 发送状态
     */
    @TableField("send_status")
    private MessageDetailStatusEnum sendStatus;

    /**
     * 三方渠道返回唯一标识
     */
    @TableField("out_serial_number")
    private String outSerialNumber;

    /**
     * 三方渠道返回错误信息
     */
    @TableField("out_resp")
    private String outResp;

    /**
     * 实际发送完成时间
     */
    @TableField("finish_time")
    private Date finishTime;

    /**
     * 已重试次数
     */
    @TableField("retry_count")
    private Integer retryCount;

    /**
     * 最大重试次数
     */
    @TableField("max_retry")
    private Integer maxRetry;

    /**
     * 下次重试时间
     */
    @TableField("next_retry_time")
    private Date nextRetryTime;

    /**
     * 租户编号
     */
    @TableField("tenant_id")
    private Long tenantId;

}
