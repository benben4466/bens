package cn.ibenbeni.bens.iot.api.enums.thingmodel;

import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * IOT 物模型属性访问数据方式枚举
 */
@Getter
@AllArgsConstructor
public enum IotThingModelAccessModeEnum implements ReadableEnum<IotThingModelAccessModeEnum> {

    READ_ONLY("r", "只读"),
    WRITE_ONLY("w", "只写"),
    READ_WRITE("rw", "读写"),

    ;

    public static final String[] ARRAYS = Arrays.stream(values()).map(IotThingModelAccessModeEnum::getMode).toArray(String[]::new);

    /**
     * 访问数据方式编号
     */
    private final String mode;

    /**
     * 描述
     */
    private final String description;

    @Override
    public Object getKey() {
        return mode;
    }

    @Override
    public Object getName() {
        return description;
    }

    @Override
    public IotThingModelAccessModeEnum parseToEnum(String mode) {
        for (IotThingModelAccessModeEnum value : values()) {
            if (value.getMode().equals(mode)) {
                return value;
            }
        }

        return null;
    }

    @Override
    public String[] compareValueArray() {
        return ARRAYS;
    }

}
