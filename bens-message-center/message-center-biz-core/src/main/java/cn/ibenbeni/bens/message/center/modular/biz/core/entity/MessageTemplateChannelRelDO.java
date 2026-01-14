package cn.ibenbeni.bens.message.center.modular.biz.core.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息模板内容与渠道配置关系实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("message_template_channel_rel")
public class MessageTemplateChannelRelDO extends BaseBusinessEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 消息模板内容ID
     */
    @TableField("template_content_id")
    private Long templateContentId;

    /**
     * 渠道配置ID
     */
    @TableField("channel_config_id")
    private Long channelConfigId;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

}
