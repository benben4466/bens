package cn.ibenbeni.bens.rule.enums.user;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    ADMIN(10, "管理员"),

    ;

    private final Integer type;

    private final String message;

    public static UserTypeEnum parseToEnum(Integer type) {
        return ArrayUtil.firstMatch(item -> item.getType().equals(type), values());
    }

}
