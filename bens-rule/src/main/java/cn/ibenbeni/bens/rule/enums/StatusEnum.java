package cn.ibenbeni.bens.rule.enums;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * 公共状态，一般用来表示开启和关闭
 *
 * @author benben
 * @date 2025/4/19  下午12:47
 */
@Getter
public enum StatusEnum implements ReadableEnum<StatusEnum> {

    /**
     * 启用
     */
    ENABLE(1, "启用"),

    /**
     * 禁用
     */
    DISABLE(2, "禁用");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(StatusEnum::getCode).toArray(Integer[]::new);

    // @EnumValue
    // @JsonValue
    private final Integer code;

    private final String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获取枚举
     */
    public static StatusEnum codeToEnum(Integer code) {
        if (null != code) {
            for (StatusEnum item : StatusEnum.values()) {
                if (item.getCode().equals(code)) {
                    return item;
                }
            }
        }
        return null;
    }

    public static boolean isDisable(Integer code) {
        return ObjUtil.equals(DISABLE.code, code);
    }

    @Override
    public Object getKey() {
        return this.code;
    }

    @Override
    public Object getName() {
        return this.message;
    }

    @Override
    public StatusEnum parseToEnum(String originValue) {
        if (ObjectUtil.isEmpty(originValue)) {
            return null;
        }
        for (StatusEnum value : StatusEnum.values()) {
            if (value.code.equals(Convert.toInt(originValue))) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
