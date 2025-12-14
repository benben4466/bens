package cn.ibenbeni.bens.iot.modular.base.service.rule.matcher;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.expression.ExpressionUtil;
import cn.hutool.json.JSONUtil;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleConditionOperatorEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IOT-场景规则-匹配器工具类
 * <p>作用：提供通用的条件评估逻辑和工具方法，供触发器和条件匹配器使用</p>
 */
@Slf4j
public class IotSceneRuleMatcherHelper {

    // 私有构造方法
    private IotSceneRuleMatcherHelper() {
    }

    /**
     * 评估 EL 表达式是否匹配
     *
     * @param operator    操作符
     * @param sourceValue 源值
     * @param paramValue  参数值
     * @return 匹配结果；true=匹配成功；false=匹配失败；
     */
    public static boolean evaluateELCondition(String operator, Object sourceValue, String paramValue) {
        try {
            IotSceneRuleConditionOperatorEnum operatorEnum = IotSceneRuleConditionOperatorEnum.operatorOf(operator);
            if (operatorEnum == null) {
                log.warn("[evaluateELCondition][暂无该条件操作符, 操作符: {}]", operator);
                return false;
            }

            // 计算
            return evaluateConditionWithOperatorEnum(operatorEnum, sourceValue, paramValue);
        } catch (Exception ex) {
            log.error("[evaluateELCondition][EL条件评估错误, 操作符: {}, 来源值: {}, 参数值: {}]", operator, sourceValue, paramValue, ex);
            return false;
        }
    }

    /**
     * 根据条件操作符枚举，评估 EL 表达式是否匹配
     *
     * @param operatorEnum 条件操作符枚举
     * @param sourceValue  源值
     * @param paramValue   参数值
     * @return 匹配结果；true=匹配成功；false=匹配失败；
     */
    private static boolean evaluateConditionWithOperatorEnum(IotSceneRuleConditionOperatorEnum operatorEnum, Object sourceValue, String paramValue) {
        try {
            // 构建 EL 表达式参数
            Map<String, Object> stringObjectMap = buildELVariableMap(operatorEnum, sourceValue, paramValue);
            // 执行EL表达式
            return (Boolean) parseELExpression(operatorEnum.getSpringExpression(), stringObjectMap);
        } catch (Exception ex) {
            log.error("[evaluateConditionWithOperatorEnum][EL条件评估错误, 操作符: {}, 来源值: {}, 参数值: {}]", operatorEnum, sourceValue, paramValue, ex);
            return false;
        }
    }

    /**
     * 构建 EL 表达式参数
     *
     * @param operatorEnum 条件操作符枚举
     * @param sourceValue  源值
     * @param paramValue   参数值
     * @return EL表达式参数
     */
    private static Map<String, Object> buildELVariableMap(IotSceneRuleConditionOperatorEnum operatorEnum, Object sourceValue, String paramValue) {
        Map<String, Object> variableMap = new HashMap<>();
        // 设置原值
        variableMap.put(IotSceneRuleConditionOperatorEnum.SPRING_EXPRESSION_SOURCE, String.valueOf(sourceValue));

        if (StrUtil.isNotBlank(paramValue)) {
            if (JSONUtil.isTypeJSONArray(paramValue)) {
                // 说明是多值
                List<String> values = JSONUtil.toList(paramValue, String.class);
                variableMap.put(IotSceneRuleConditionOperatorEnum.SPRING_EXPRESSION_VALUE_LIST, values);
            } else {
                // 说明是单值
                variableMap.put(IotSceneRuleConditionOperatorEnum.SPRING_EXPRESSION_VALUE, paramValue);
            }
        }

        return variableMap;
    }

    private static Object parseELExpression(String expression, Map<String, Object> variableMap) {
        if (StrUtil.isBlank(expression)) {
            return false;
        }

        try {
            // IotSceneRuleConditionOperatorEnum 中 SpringEL 表达式执行结果一定是布尔值
            return ExpressionUtil.eval(expression, variableMap);
        } catch (Exception ex) {
            log.error("[parseELExpression][EL表达式: {}, 参数: {}, 原因: {}]", expression, variableMap, ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 检查标识符是否匹配
     *
     * @param expectedIdentifier 期望标识符
     * @param actualIdentifier   实际标识符
     * @return 是否匹配
     */
    public static boolean isIdentifierMatched(String expectedIdentifier, String actualIdentifier) {
        return StrUtil.isNotBlank(expectedIdentifier) && expectedIdentifier.equals(actualIdentifier);
    }

}
