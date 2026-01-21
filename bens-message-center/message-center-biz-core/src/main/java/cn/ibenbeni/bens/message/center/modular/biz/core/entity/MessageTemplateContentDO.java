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

import java.util.Map;

/**
 * 消息模板内容实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "message_template_content", autoResultMap = true)
public class MessageTemplateContentDO extends BaseBusinessEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 模板ID
     */
    @TableField("template_id")
    private Long templateId;

    /**
     * 渠道类型
     */
    @TableField("channel_type")
    private Integer channelType;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 模板内容
     */
    @TableField("template_content")
    private String templateContent;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;
}

