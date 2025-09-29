package cn.ibenbeni.bens.message.center.modular.notify.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.db.mp.type.StringListTypeHandler;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 通知公告实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_notify_template", autoResultMap = true)
public class NotifyTemplateDO extends BaseBusinessEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 模板名称
     */
    @TableField("name")
    private String name;

    /**
     * 模板编码
     */
    @TableField("code")
    private String code;

    /**
     * 发送人名称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 模板内容
     */
    @TableField("content")
    private String content;

    /**
     * 模板类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 模板参数
     */
    @TableField(value = "params", typeHandler = StringListTypeHandler.class)
    private List<String> params;

    /**
     * 模板状态
     */
    @TableField("status_flag")
    private Integer statusFlag;

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
