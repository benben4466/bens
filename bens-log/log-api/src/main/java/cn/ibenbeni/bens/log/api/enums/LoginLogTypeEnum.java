package cn.ibenbeni.bens.log.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录日志类型枚举
 */
@Getter
@AllArgsConstructor
public enum LoginLogTypeEnum {

    LOGIN_USERNAME(100, "使用账号登录"),

    LOGOUT_SELF(200, "自己主动登出"),
    LOGOUT_DELETE(202, "强制退出"),
    ;

    /**
     * 日志类型
     */
    private final Integer type;

    /**
     * 日志描述
     */
    private final String message;

}
