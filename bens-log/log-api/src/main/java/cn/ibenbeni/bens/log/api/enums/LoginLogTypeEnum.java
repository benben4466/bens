package cn.ibenbeni.bens.log.api.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 登录日志类型枚举
 */
@Getter
@AllArgsConstructor
public enum LoginLogTypeEnum implements ReadableEnum<LoginLogTypeEnum> {

    LOGIN_USERNAME(100, "使用账号登录"),

    LOGOUT_SELF(200, "自己主动登出"),
    LOGOUT_DELETE(202, "强制退出"),
    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(LoginLogTypeEnum::getType).toArray(Integer[]::new);

    /**
     * 日志类型
     */
    private final Integer type;

    /**
     * 日志描述
     */
    private final String message;

    @Override
    public Object getKey() {
        return type;
    }

    @Override
    public Object getName() {
        return message;
    }

    @Override
    public LoginLogTypeEnum parseToEnum(String type) {
        return ArrayUtil.firstMatch(item -> item.getType().equals(Integer.valueOf(type)), values());
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
