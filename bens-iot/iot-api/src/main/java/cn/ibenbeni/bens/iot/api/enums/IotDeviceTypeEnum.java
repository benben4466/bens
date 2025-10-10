package cn.ibenbeni.bens.iot.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * IOT设备类型枚举
 */
@Getter
@AllArgsConstructor
public enum IotDeviceTypeEnum {

    NON_NETWORKED_DEVICE(0, "非网关设备"),
    GATEWAY_DEVICE(1, "网关设备"),
    GATEWAY_SUB_DEVICE(2, "网关子设备"),
    DIRECTLY_CONNECTED_DEVICE(3, "直连设备"),
    MONITOR_DEVICE(4, "监控设备"),

    ;

    private final Integer code;

    private final String description;

}
