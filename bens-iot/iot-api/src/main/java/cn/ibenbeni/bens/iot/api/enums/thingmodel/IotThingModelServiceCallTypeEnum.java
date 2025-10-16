package cn.ibenbeni.bens.iot.api.enums.thingmodel;

import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum IotThingModelServiceCallTypeEnum implements ReadableEnum<IotThingModelServiceCallTypeEnum> {

    ASYNC("async", "异步调用"),
    SYNC("sync", "同步调用"),

    ;

    public static final String[] ARRAYS = Arrays.stream(values()).map(IotThingModelServiceCallTypeEnum::getType).toArray(String[]::new);

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
    public IotThingModelServiceCallTypeEnum parseToEnum(String originValue) {
        for (IotThingModelServiceCallTypeEnum value : values()) {
            if (value.getType().equals(originValue)) {
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
