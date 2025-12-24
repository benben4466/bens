package cn.ibenbeni.bens.iot.api.enums.thingmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * IoT-物模型类型枚举
 *
 * @author ahh
 */
@Getter
@AllArgsConstructor
public enum IotThingModelTypeEnum {

    PROPERTY(1, "属性"),
    SERVICE(2, "服务"),
    EVENT(3, "事件"),
    ;

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String description;

}
