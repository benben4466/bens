package cn.ibenbeni.bens.message.center.modular.biz.core.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "message_template", autoResultMap = true)
public class MessageTemplateDO extends BaseBusinessEntity {

    /**
     * 模板ID
     */
    @TableId(value = "template_id", type = IdType.ASSIGN_ID)
    private Long templateId;

    /**
     * 模板编码
     */
    @TableField("template_code")
    private String templateCode;

    /**
     * 模板名称
     */
    @TableField("template_name")
    private String templateName;

    /**
     * 模板状态
     */
    @TableField("template_status")
    private Integer templateStatus;

    /**
     * 业务类型
     */
    @TableField("biz_type")
    private String bizType;

    /**
     * 支持渠道列表
     */
    @TableField(value = "support_channels", typeHandler = JacksonTypeHandler.class)
    private List<Integer> supportChannels;

    /**
     * 审核状态
     */
    @TableField("audit_status")
    private Integer auditStatus;

    /**
     * 审核时间
     */
    @TableField("audit_time")
    private LocalDateTime auditTime;

    /**
     * 审核人ID
     */
    @TableField("audit_user_id")
    private Long auditUserId;

    /**
     * 审核意见
     */
    @TableField("audit_comment")
    private String auditComment;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

    /**
     * 消息模板内容表
     */
    @TableField(exist = false)
    private List<MessageTemplateContentDO> contentList;

}

