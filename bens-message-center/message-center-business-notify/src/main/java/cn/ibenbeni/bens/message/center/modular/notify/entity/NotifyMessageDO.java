package cn.ibenbeni.bens.message.center.modular.notify.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.Fastjson2TypeHandler;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 通知公告实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_notify_message", autoResultMap = true)
public class NotifyMessageDO extends BaseBusinessEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户类型
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 模版ID
     */
    @TableField("template_id")
    private Long templateId;

    /**
     * 模板编码
     */
    @TableField("template_code")
    private String templateCode;

    /**
     * 模版发送人名称
     */
    @TableField("template_nickname")
    private String templateNickname;

    /**
     * 模版内容
     */
    @TableField("template_content")
    private String templateContent;

    /**
     * 模版类型
     */
    @TableField("template_type")
    private Integer templateType;

    /**
     * 模版参数
     */
    @TableField(value = "template_params", typeHandler = Fastjson2TypeHandler.class)
    private Map<String, Object> templateParams;

    /**
     * 是否已读
     */
    @TableField("read_status")
    private Boolean readStatus;

    /**
     * 阅读时间
     */
    @TableField(value = "read_time")
    private LocalDateTime readTime;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

}
