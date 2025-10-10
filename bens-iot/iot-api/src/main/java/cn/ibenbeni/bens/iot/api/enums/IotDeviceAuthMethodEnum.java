package cn.ibenbeni.bens.iot.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * IOT设备认证方式枚举
 */
@Getter
@AllArgsConstructor
public enum IotDeviceAuthMethodEnum {

    SIMPLE_AUTH(0, "简单认证"),
    ENCRYPTION_AUTH(1, "加密认证"),
    SIMPLE_AND_ENCRYPTION_AUTH(2, "简单+加密认证"),

    ;

    private final Integer code;

    private final String description;

}
