package cn.ibenbeni.bens.message.center.modular.biz.core.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
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
 * 消息渠道配置实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "message_channel_config", autoResultMap = true)
public class MessageChannelConfigDO extends BaseBusinessEntity {

    /**
     * 配置ID
     */
    @TableId(value = "config_id", type = IdType.ASSIGN_ID)
    private Long configId;

    /**
     * 渠道编码
     */
    @TableField("channel_code")
    private String channelCode;

    /**
     * 渠道名称
     */
    @TableField("channel_name")
    private String channelName;

    /**
     * 渠道类型
     */
    @TableField("channel_type")
    private Integer channelType;

    /**
     * 账户配置
     */
    @TableField(value = "account_config", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> accountConfig;

    /**
     * 状态
     */
    @TableField("status_flag")
    private StatusEnum statusFlag;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

}
