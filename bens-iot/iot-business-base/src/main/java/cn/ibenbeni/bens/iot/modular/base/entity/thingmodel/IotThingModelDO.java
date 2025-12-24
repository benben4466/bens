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
 * IOT-物模型实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "iot_thing_model", autoResultMap = true)
public class IotThingModelDO extends BaseBusinessEntity {

    /**
     * 物模型ID
     */
    @TableId(value = "model_id", type = IdType.ASSIGN_ID)
    private Long modelId;

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
     * 产品ID
     */
    @TableField("product_id")
    private Long productId;

    /**
     * 产品KEY
     */
    @TableField("product_key")
    private String productKey;

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
     * 物模型排序
     */
    @TableField("model_sort")
    private BigDecimal modelSort;

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
