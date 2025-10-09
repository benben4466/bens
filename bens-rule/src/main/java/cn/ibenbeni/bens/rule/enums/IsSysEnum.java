package cn.ibenbeni.bens.rule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统内置枚举
 */
@Getter
@AllArgsConstructor
public enum IsSysEnum {

    SYS(0, "内置"),

    CUSTOMIZE(1, "自定义");

    private final Integer code;

    private final String message;

    public static boolean isSys(Integer code) {
        return SYS.code.equals(code);
    }

}
