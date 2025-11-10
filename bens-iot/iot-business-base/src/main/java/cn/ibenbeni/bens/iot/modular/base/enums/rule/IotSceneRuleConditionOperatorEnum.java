package cn.ibenbeni.bens.iot.modular.base.enums.rule;

import cn.hutool.core.util.ArrayUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * IOT-场景触发条件的操作符枚举
 */
@Getter
@RequiredArgsConstructor
public enum IotSceneRuleConditionOperatorEnum implements ReadableEnum<IotSceneRuleConditionOperatorEnum> {

    EQUALS("=", "#source == #value", "相等"),
    NOT_EQUALS("!=", "!(#source == #value)", "不相等"),

    GREATER_THAN(">", "#source > #value", "大于"),
    GREATER_THAN_OR_EQUALS(">=", "#source >= #value", "大于等于"),

    LESS_THAN("<", "#source < #value", "小于"),
    LESS_THAN_OR_EQUALS("<=", "#source <= #value", "小于等于"),

    IN("in", "#values.contains(#source)", "包含"),
    NOT_IN("not in", "!(#values.contains(#source))", "不包含"),

    BETWEEN("between", "(#source >= #values.get(0)) && (#source <= #values.get(1))", "在...之间"),
    NOT_BETWEEN("not between", "(#source < #values.get(0)) || (#source > #values.get(1))", "不在...之间"),

    DATE_TIME_GREATER_THAN("date_time_>", "#source > #value", "在时间之后: 时间戳"),
    DATE_TIME_LESS_THAN("date_time_<", "#source < #value", "在时间之前：时间戳"),
    DATE_TIME_BETWEEN("date_time_between", "(#source >= #values.get(0)) && (#source <= #values.get(1))", "在时间之间：时间戳"),

    TIME_GREATER_THAN("time_>", "#source.isAfter(#value)", "在当日时间之后：HH:mm:ss"),
    TIME_LESS_THAN("time_<", "#source.isBefore(#value)", "在当日时间之前：HH:mm:ss"),
    TIME_BETWEEN("time_between", "(#source >= #values.get(0)) && (#source <= #values.get(1))","在当日时间之间：HH:mm:ss")

    ;

    /**
     * 操作符
     */
    private final String operator;

    /**
     * SpEL表达式
     */
    private final String springExpression;

    /**
     * 描述
     */
    private final String description;

    public static final String[] ARRAYS = Arrays.stream(values()).map(IotSceneRuleConditionOperatorEnum::getOperator).toArray(String[]::new);

    /**
     * Spring 表达式 - 原始值
     */
    public static final String SPRING_EXPRESSION_SOURCE = "source";

    /**
     * Spring 表达式 - 目标值
     */
    public static final String SPRING_EXPRESSION_VALUE = "value";

    /**
     * Spring 表达式 - 目标值数组
     */
    public static final String SPRING_EXPRESSION_VALUE_LIST = "values";

    @Override
    public Object getKey() {
        return operator;
    }

    @Override
    public Object getName() {
        return description;
    }

    @Override
    public IotSceneRuleConditionOperatorEnum parseToEnum(String originValue) {
        return ArrayUtil.firstMatch(item -> item.getOperator().equals(operator), values());
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
