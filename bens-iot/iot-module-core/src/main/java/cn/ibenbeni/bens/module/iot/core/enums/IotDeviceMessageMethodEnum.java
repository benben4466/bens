package cn.ibenbeni.bens.module.iot.core.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import cn.ibenbeni.bens.rule.util.SetUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

/**
 * IoT 设备消息的方法枚举
 */
@Getter
@AllArgsConstructor
public enum IotDeviceMessageMethodEnum implements ReadableEnum<IotDeviceMessageMethodEnum> {

    // region 设备状态

    STATE_UPDATE("thing.state.update", "设备状态更新", true),

    // endregion

    // region 设备属性

    PROPERTY_POST("thing.property.post", "属性上报", true),
    PROPERTY_SET("thing.property.set", "属性设置", false),

    // endregion

    // region 设备事件

    EVENT_POST("thing.event.post", "事件上报", true),

    // endregion

    // region 设备服务调用

    SERVICE_INVOKE("thing.service.invoke", "服务调用", false),

    // endregion

    ;

    private final String method;

    private final String name;

    private final Boolean upstream;

    public static final String[] ARRAYS = Arrays.stream(values()).map(IotDeviceMessageMethodEnum::getMethod).toArray(String[]::new);


    /**
     * 不进行 reply 回复的方法集合
     */
    public static final Set<String> REPLY_DISABLED = SetUtils.asSet(
            STATE_UPDATE.getMethod()
    );

    @Override
    public Object getKey() {
        return method;
    }

    @Override
    public Object getName() {
        return name;
    }

    @Override
    public IotDeviceMessageMethodEnum parseToEnum(String method) {
        for (IotDeviceMessageMethodEnum value : values()) {
            if (value.method.equals(method)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

    public static IotDeviceMessageMethodEnum of(String method) {
        return ArrayUtil.firstMatch(item -> item.getMethod().equals(method),
                IotDeviceMessageMethodEnum.values());
    }

    /**
     * 是否进行 reply 回复
     *
     * @param method 方法
     * @return 是否进行 reply 回复
     */
    public static boolean isReplyDisabled(String method) {
        return REPLY_DISABLED.contains(method);
    }

}
