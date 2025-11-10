package cn.ibenbeni.bens.iot.modular.base.entity.rule;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import cn.ibenbeni.bens.iot.modular.base.entity.thingmodel.IotThingModelDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleActionTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleConditionOperatorEnum;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleConditionTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleTriggerTypeEnum;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.List;

/**
 * IOT场景联动规则实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "iot_scene_rule", autoResultMap = true)
public class IotSceneRuleDO extends BaseBusinessEntity {

    // region 实体属性

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 状态
     * <p>枚举：{@link StatusEnum}</p>
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 触发器数组
     */
    @TableField(value = "triggers", typeHandler = JacksonTypeHandler.class)
    private List<Trigger> triggers;

    /**
     * 执行器数组
     */
    @TableField(value = "actions", typeHandler = JacksonTypeHandler.class)
    private List<Action> actions;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

    // endregion

    // region 触发器

    /**
     * 场景规则触发器
     */
    @Data
    public static class Trigger {

        /**
         * 场景触发类型
         * <p>枚举值：{@link IotSceneRuleTriggerTypeEnum}</p>
         */
        private Integer type;

        /**
         * 产品ID
         */
        private Long productId;

        /**
         * 设备ID
         */
        private Long deviceId;

        /**
         * 物模型标识符
         * <p>对应 {@link IotThingModelDO#getIdentifier()}</p>
         */
        private String identifier;

        /**
         * 操作符
         * <p>枚举：{@link IotSceneRuleConditionOperatorEnum}</p>
         */
        private String operator;

        /**
         * 参数
         * <p>若有多个值，则使用 "," 分割，如：1,2,3</p>
         */
        private String param;

        /**
         * CRON 表达式
         */
        private String cronExpression;

        // region 触发条件

        /**
         * 触发条件分组（状态条件分组）的数组
         * <p>
         * 第一层 List：分组与分组之间，是“或”的关系
         * 第二层 List：条件与条件之间，是“与”的关系
         * </p>
         */
        private List<List<TriggerCondition>> conditionGroups;

        // endregion
    }

    /**
     * 场景规则触发条件
     */
    @Data
    public static class TriggerCondition {

        /**
         * 触发条件类型
         * <p>枚举：{@link IotSceneRuleConditionTypeEnum}</p>
         */
        private Integer type;

        /**
         * 产品ID
         */
        private Long productId;

        /**
         * 设备ID
         */
        private Long deviceId;

        /**
         * 操作符
         * <p>枚举：{@link IotSceneRuleConditionOperatorEnum}</p>
         */
        private String operator;

        /**
         * 参数
         * <p>若有多个值，则使用 "," 分割，如：1,2,3</p>
         */
        private String param;

    }

    // endregion

    // region 执行器

    /**
     * 场景规则执行器
     */
    @Data
    public static class Action {

        /**
         * 执行器类型
         * <p>枚举：{@link IotSceneRuleActionTypeEnum}</p>
         */
        private Integer type;

        /**
         * 产品ID
         */
        private Long productId;

        /**
         * 设备ID
         */
        private Long deviceId;

        /**
         * 操作符
         */
        private String operator;

        /**
         * 参数
         */
        private String params;

    }

    // endregion

}
