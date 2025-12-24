package cn.ibenbeni.bens.iot.api.enums.device;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * IOT设备联网方式枚举
 */
@Getter
@AllArgsConstructor
public enum IotDeviceNetworkMethodEnum {

    OTHER(0, "其他"),
    WIFI(1, "WIFI"),
    CELLULAR(2, "蜂窝"),
    ETHERNET(3, "以太网"),

    ;

    private final Integer code;

    private final String description;

}
