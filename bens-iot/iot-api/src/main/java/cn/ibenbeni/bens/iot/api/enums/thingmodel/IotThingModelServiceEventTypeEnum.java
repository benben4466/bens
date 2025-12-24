package cn.ibenbeni.bens.iot.api.enums.thingmodel;

import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum IotThingModelServiceEventTypeEnum implements ReadableEnum<IotThingModelServiceEventTypeEnum> {

    INFO("info", "信息"),
    ALERT("alert", "告警"),
    ERROR("error", "故障"),
    ;

    public static final String[] ARRAYS = Arrays.stream(values()).map(IotThingModelServiceEventTypeEnum::getType).toArray(String[]::new);

    private final String type;

    /**
     * 描述
     */
    private final String description;

    @Override
    public Object getKey() {
        return type;
    }

    @Override
    public Object getName() {
        return description;
    }

    @Override
    public IotThingModelServiceEventTypeEnum parseToEnum(String type) {
        for (IotThingModelServiceEventTypeEnum value : values()) {
            if (value.getType().equals(type)) {
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
