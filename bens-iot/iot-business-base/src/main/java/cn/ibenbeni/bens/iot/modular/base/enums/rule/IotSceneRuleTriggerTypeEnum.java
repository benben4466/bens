package cn.ibenbeni.bens.iot.modular.base.enums.rule;

import cn.ibenbeni.bens.module.iot.core.enums.IotDeviceMessageMethodEnum;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * IOT-场景联动规则触发方式枚举
 */
@Getter
@RequiredArgsConstructor
public enum IotSceneRuleTriggerTypeEnum implements ReadableEnum<IotSceneRuleTriggerTypeEnum> {

    /**
     * 对应 {@link IotDeviceMessageMethodEnum#STATE_UPDATE}
     */
    DEVICE_STATE_UPDATE(1, "设备状态变更"),

    /**
     * 对应 {@link IotDeviceMessageMethodEnum#PROPERTY_POST}
     */
    DEVICE_PROPERTY_POST(2, "物模型属性上报"),

    /**
     * 对应 {@link IotDeviceMessageMethodEnum#EVENT_POST}
     */
    DEVICE_EVENT_POST(3, "物模型事件上报"),

    /**
     * 对应 {@link IotDeviceMessageMethodEnum#SERVICE_INVOKE}
     */
    DEVICE_SERVICE_INVOKE(4, "物模型服务调用"),

    TIMER(100, "定时触发"),

    ;

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String description;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(IotSceneRuleTriggerTypeEnum::getType).toArray(Integer[]::new);

    @Override
    public Object getKey() {
        return type;
    }

    @Override
    public Object getName() {
        return description;
    }

    @Override
    public IotSceneRuleTriggerTypeEnum parseToEnum(String originValue) {
        for (IotSceneRuleTriggerTypeEnum value : values()) {
            if (value.type.equals(Integer.valueOf(originValue))) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
