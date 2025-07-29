package cn.ibenbeni.bens.config.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConfigTypeEnum {

    SYSTEM(1, "系统配置"),

    CUSTOM(2, "自定义配置"),

    ;

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

}
