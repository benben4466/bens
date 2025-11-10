package cn.ibenbeni.bens.iot.modular.base.enums.rule;

import cn.hutool.core.util.ArrayUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * IOT-场景触发条件类型枚举
 */
@Getter
@RequiredArgsConstructor
public enum IotSceneRuleConditionTypeEnum implements ReadableEnum<IotSceneRuleConditionTypeEnum> {

    DEVICE_STATE(1, "设备状态"),
    DEVICE_PROPERTY(2, "设备属性"),

    CURRENT_TIME(100, "当前时间"),

    ;

    /**
     * 触发类型
     */
    private final Integer type;

    /**
     * 触发类型名称
     */
    private final String name;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(IotSceneRuleConditionTypeEnum::getType).toArray(Integer[]::new);

    @Override
    public Object getKey() {
        return type;
    }

    @Override
    public Object getName() {
        return name;
    }

    @Override
    public IotSceneRuleConditionTypeEnum parseToEnum(String originValue) {
        return ArrayUtil.firstMatch(item -> item.getType().equals(Integer.valueOf(originValue)), values());
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
