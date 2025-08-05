package cn.ibenbeni.bens.config.api.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ConfigTypeEnum implements ReadableEnum<ConfigTypeEnum> {

    SYSTEM(1, "系统配置"),

    CUSTOM(2, "自定义配置"),

    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(ConfigTypeEnum::getTypeCode).toArray(Integer[]::new);

    private final Integer typeCode;

    private final String typeName;

    public static ConfigTypeEnum parseToEnum(Integer typeCode) {
        for (ConfigTypeEnum value : values()) {
            if (value.typeCode.equals(typeCode)) {
                return value;
            }
        }

        return null;
    }

    @Override
    public Object getKey() {
        return typeCode;
    }

    @Override
    public Object getName() {
        return typeName;
    }

    @Override
    public ConfigTypeEnum parseToEnum(String typeCode) {
        return ArrayUtil.firstMatch(item -> item.getTypeCode().equals(Integer.valueOf(typeCode)), values());
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
