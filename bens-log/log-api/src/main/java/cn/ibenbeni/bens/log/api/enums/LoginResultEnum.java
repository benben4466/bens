package cn.ibenbeni.bens.log.api.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 登录日志结果枚举
 */
@Getter
@AllArgsConstructor
public enum LoginResultEnum implements ReadableEnum<LoginResultEnum> {

    SUCCESS(0, "登陆成功"),
    BAD_CREDENTIALS(10, "账号密码不正确"),

    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(LoginResultEnum::getResult).toArray(Integer[]::new);

    /**
     * 登陆结果
     */
    private final Integer result;

    /**
     * 登陆结果描述
     */
    private final String message;

    @Override
    public Object getKey() {
        return result;
    }

    @Override
    public Object getName() {
        return message;
    }

    @Override
    public LoginResultEnum parseToEnum(String result) {
        return ArrayUtil.firstMatch(item -> item.getResult().equals(Integer.valueOf(result)), values());
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
