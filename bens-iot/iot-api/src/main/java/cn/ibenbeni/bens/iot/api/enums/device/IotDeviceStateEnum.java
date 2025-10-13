package cn.ibenbeni.bens.iot.api.enums.device;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * IOT设备通信协议枚举
 */
@Getter
@AllArgsConstructor
public enum IotDeviceStateEnum {

    INACTIVE(0, "未激活"),
    DISABLE(1, "禁用"),
    ONLINE(2, "在线"),
    OFFLINE(3, "离线"),

    ;

    /**
     * 状态
     */
    private final Integer state;

    /**
     * 状态名
     */
    private final String name;

}
