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

    @TableId(value = "template_id", type = IdType.ASSIGN_ID)
    private Long templateId;

    @TableField("template_code")
    private String templateCode;

    @TableField("template_name")
    private String templateName;

    @TableField("template_status")
    private Integer templateStatus;

    @TableField("biz_type")
    private String bizType;

    @TableField(value = "support_channels", typeHandler = JacksonTypeHandler.class)
    private List<Integer> supportChannels;

    @TableField("audit_status")
    private Integer auditStatus;

    @TableField("audit_time")
    private LocalDateTime auditTime;

    @TableField("audit_user_id")
    private Long auditUserId;

    @TableField("audit_comment")
    private String auditComment;

    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;
}

