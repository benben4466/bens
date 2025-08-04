package cn.ibenbeni.bens.log.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录日志结果枚举
 */
@Getter
@AllArgsConstructor
public enum LoginResultEnum {

    SUCCESS(0, "登陆成功"),
    BAD_CREDENTIALS(10, "账号密码不正确"),

    ;

    /**
     * 登陆结果
     */
    private final Integer result;

    /**
     * 登陆结果描述
     */
    private final String message;

}
