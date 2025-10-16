package cn.ibenbeni.bens.iot.api.enums.thingmodel;

import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum IotThingModelParamDirectionEnum implements ReadableEnum<IotThingModelParamDirectionEnum> {

    INPUT("input", "输入参数"),
    OUTPUT("output", "输出参数"),
    ;

    public static final String[] ARRAYS = Arrays.stream(values()).map(IotThingModelParamDirectionEnum::getDirection).toArray(String[]::new);

    /**
     * 方向
     */
    private final String direction;

    /**
     * 描述
     */
    private final String description;

    @Override
    public Object getKey() {
        return direction;
    }

    @Override
    public Object getName() {
        return description;
    }

    @Override
    public IotThingModelParamDirectionEnum parseToEnum(String direction) {
        for (IotThingModelParamDirectionEnum value : values()) {
            if (value.getDirection().equals(direction)) {
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
