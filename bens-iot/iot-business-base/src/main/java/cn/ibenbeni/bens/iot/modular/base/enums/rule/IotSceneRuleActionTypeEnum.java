package cn.ibenbeni.bens.iot.modular.base.enums.rule;

import cn.hutool.core.util.ArrayUtil;
import cn.ibenbeni.bens.module.iot.core.enums.IotDeviceMessageMethodEnum;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * IOT-场景执行器类型
 */
@Getter
@RequiredArgsConstructor
public enum IotSceneRuleActionTypeEnum implements ReadableEnum<IotSceneRuleActionTypeEnum> {

    /**
     * <p>对应 {@link IotDeviceMessageMethodEnum#PROPERTY_SET}</p>
     */
    DEVICE_PROPERTY_SET(1, "设备属性设置"),

    /**
     * 对应 {@link IotDeviceMessageMethodEnum#SERVICE_INVOKE}
     */
    DEVICE_SERVICE_INVOKE(2, "设备服务调用"),

    ;

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String description;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(IotSceneRuleActionTypeEnum::getType).toArray(Integer[]::new);

    @Override
    public Object getKey() {
        return type;
    }

    @Override
    public Object getName() {
        return description;
    }

    @Override
    public IotSceneRuleActionTypeEnum parseToEnum(String originValue) {
        return ArrayUtil.firstMatch(item -> item.getType().equals(Integer.valueOf(originValue)), values());
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
