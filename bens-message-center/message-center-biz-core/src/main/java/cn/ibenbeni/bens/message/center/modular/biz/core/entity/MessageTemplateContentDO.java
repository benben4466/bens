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

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "message_template_content", autoResultMap = true)
public class MessageTemplateContentDO extends BaseBusinessEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("template_id")
    private Long templateId;

    @TableField("channel_type")
    private Integer channelType;

    @TableField("title")
    private String title;

    @TableField("template_content")
    private String templateContent;

    @TableField(value = "params_config", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> paramsConfig;

    @TableField(value = "channel_config", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> channelConfig;

    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;
}

