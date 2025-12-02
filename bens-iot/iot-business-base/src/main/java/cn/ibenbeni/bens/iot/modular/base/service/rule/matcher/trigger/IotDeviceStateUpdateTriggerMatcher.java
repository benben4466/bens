package cn.ibenbeni.bens.iot.modular.base.service.rule.matcher.trigger;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.expression.ExpressionUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleConditionOperatorEnum;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleTriggerTypeEnum;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * IOT-场景规则-设备状态更新触发器匹配器
 */
@Slf4j
@Component
public class IotDeviceStateUpdateTriggerMatcher implements IotSceneRuleTriggerMatcher {

    @Override
    public IotSceneRuleTriggerTypeEnum getSupportedTriggerType() {
        return IotSceneRuleTriggerTypeEnum.DEVICE_STATE_UPDATE;
    }

    @Override
    public boolean matches(IotDeviceMessage message, IotSceneRuleDO.Trigger trigger) {
        // 校验触发器是否为空、类型是否为空
        if (trigger == null || trigger.getType() == null) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 触发器为空或者类型为空]", message, trigger);
            return false;
        }

        // 校验触发器类型是否为设备状态更新
        if (!IotSceneRuleTriggerTypeEnum.DEVICE_STATE_UPDATE.getType().equals(trigger.getType())) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 触发器类型不正确, 期望类型: {}, 实际类型: {}]", message, trigger, IotSceneRuleTriggerTypeEnum.DEVICE_STATE_UPDATE.getType(), trigger.getType());
            return false;
        }

        // 校验操作符和参数
        if (trigger.getOperator() == null || trigger.getParam() == null) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 操作符或值为空]", message, trigger);
            return false;
        }

        // 获取设备状态值
        String statusVal = IotDeviceMessageUtils.getIdentifier(message);
        if (StrUtil.isBlank(statusVal)) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 设备状态值为空]", message, trigger);
            return false;
        }

        // 获取操作枚举
        IotSceneRuleConditionOperatorEnum operatorEnum = IotSceneRuleConditionOperatorEnum.operatorOf(trigger.getOperator());
        if (operatorEnum == null) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 更新设备状态触发器操作符错误]", message, trigger);
            return false;
        }

        // 构建 EL 表达式
        Map<String, Object> elVariableMap = new HashMap<>();
        elVariableMap.put(IotSceneRuleConditionOperatorEnum.SPRING_EXPRESSION_SOURCE, statusVal);
        elVariableMap.put(IotSceneRuleConditionOperatorEnum.SPRING_EXPRESSION_VALUE, trigger.getParam());
        boolean isMatch = parseELExpression(IotSceneRuleConditionOperatorEnum.EQUALS.getSpringExpression(), elVariableMap);

        if (isMatch) {
            log.info("[matches][消息: {}, 触发器: {}, 匹配成功]", message, trigger);
        } else {
            log.info("[matches][消息: {}, 触发器: {}, 原因: 匹配失败,触发器条件未满足]", message, trigger);
        }

        return isMatch;
    }

    @Override
    public int getPriority() {
        return 10;
    }

    private boolean parseELExpression(String expression, Map<String, Object> variableMap) {
        if (StrUtil.isBlank(expression)) {
            return false;
        }

        try {
            // IotSceneRuleConditionOperatorEnum 中 SpringEL 表达式执行结果一定是布尔值
            return (Boolean) ExpressionUtil.eval(expression, variableMap);
        } catch (Exception ex) {
            log.error("[validateELSyntax][EL表达式: {}, 参数: {}, 原因: {}]", expression, variableMap, ex.getMessage(), ex);
            return false;
        }
    }

}
