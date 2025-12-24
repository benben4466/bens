package cn.ibenbeni.bens.iot.modular.base.entity.thingmodel;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.iot.api.enums.thingmodel.IotThingModelTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.ThingModelEvent;
import cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.ThingModelProperty;
import cn.ibenbeni.bens.iot.modular.base.pojo.model.thingmodel.ThingModelService;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.math.BigDecimal;

/**
 * IOT-物模型模板实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "iot_thing_model_template", autoResultMap = true)
public class IotThingModelTemplateDO extends BaseBusinessEntity {

    /**
     * 物模型模板ID
     */
    @TableId(value = "template_id", type = IdType.ASSIGN_ID)
    private Long templateId;

    /**
     * 模型名称
     */
    @TableField("name")
    private String name;

    /**
     * 模型标识
     * <p>产品下唯一</p>
     */
    @TableField("identifier")
    private String identifier;

    /**
     * 模型类型
     * <p>枚举: {@link IotThingModelTypeEnum}</p>
     */
    @TableField("type")
    private Integer type;

    /**
     * 属性
     */
    @TableField(value = "property", typeHandler = JacksonTypeHandler.class)
    private ThingModelProperty property;

    /**
     * 事件
     */
    @TableField(value = "event", typeHandler = JacksonTypeHandler.class)
    private ThingModelEvent event;

    /**
     * 服务
     */
    @TableField(value = "service", typeHandler = JacksonTypeHandler.class)
    private ThingModelService service;

    /**
     * 是否系统定义
     */
    @TableField("is_sys")
    private Integer isSys;

    /**
     * 是否实时监测
     */
    @TableField("is_monitor")
    private Integer isMonitor;

    /**
     * 是否历史存储
     */
    @TableField("is_history")
    private Integer isHistory;

    /**
     * 物模型模板排序
     */
    @TableField("template_sort")
    private BigDecimal templateSort;

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
