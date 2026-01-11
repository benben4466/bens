package cn.ibenbeni.bens.message.center.modular.biz.core.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgRecipientTypeEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendFailTypeEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendStatusEnum;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 消息发送记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "message_send_record", autoResultMap = true)
public class MessageSendRecordDO extends BaseBusinessEntity {

    /**
     * 记录ID
     */
    @TableId(value = "record_id", type = IdType.ASSIGN_ID)
    private Long recordId;

    /**
     * 关联模板ID
     */
    @TableField("template_id")
    private Long templateId;

    /**
     * 模板编码
     */
    @TableField("template_code")
    private String templateCode;

    /**
     * 模板内容
     */
    @TableField(exist = false)
    private String templateContent;

    /**
     * 业务类型
     */
    @TableField("biz_type")
    private String bizType;

    /**
     * 业务关联ID
     */
    @TableField("biz_id")
    private String bizId;

    /**
     * 发送时的标题
     */
    @TableField("msg_title")
    private String msgTitle;

    /**
     * 模板参数变量
     */
    @TableField(value = "msg_variables", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> msgVariables;

    /**
     * 渠道类型
     */
    @TableField("channel_type")
    private Integer channelType;

    /**
     * 接收者信息
     */
    @TableField(value = "recipient", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> recipient;

    /**
     * 接收人类型
     */
    @TableField("recipient_type")
    private MsgRecipientTypeEnum recipientType;

    /**
     * 发送状态
     */
    @TableField("send_status")
    private MsgSendStatusEnum sendStatus;

    /**
     * 重试次数
     */
    @TableField("retry_count")
    private Integer retryCount;

    /**
     * 失败原因类型
     */
    @TableField("fail_type")
    private MsgSendFailTypeEnum failType;

    /**
     * 失败原因
     */
    @TableField("fail_reason")
    private String failReason;

    /**
     * 响应数据
     */
    @TableField("response_data")
    private String responseData;

    /**
     * 发送时间
     */
    @TableField("send_time")
    private LocalDateTime sendTime;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

}
