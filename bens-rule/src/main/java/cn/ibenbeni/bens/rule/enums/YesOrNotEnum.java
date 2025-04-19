package cn.ibenbeni.bens.rule.enums;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;

/**
 * @author benben
 */
@Getter
public enum YesOrNotEnum implements ReadableEnum<YesOrNotEnum> {

    /**
     * 是
     */
    Y("Y", "是", true),

    /**
     * 否
     */
    N("N", "否", false);

    /**
     * 使用@EnumValue注解，标记mybatis-plus保存到库中使用code值
     */
    // @EnumValue
    private final String code;

    private final String message;

    /**
     * 注解@JsonValue是返回给前端时候拿的值，而@JsonCreator是反序列化时候的方式
     */
    // @JsonValue
    private final Boolean boolFlag;

    YesOrNotEnum(String code, String message, Boolean boolFlag) {
        this.code = code;
        this.message = message;
        this.boolFlag = boolFlag;
    }

    /**
     * 根据code获取枚举，用在接收前段传参
     *
     * @author fengshuonan
     * @since 2022/9/7 17:58
     */
    // @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static YesOrNotEnum codeToEnum(Boolean boolFlag) {
        if (null != boolFlag) {
            for (YesOrNotEnum item : YesOrNotEnum.values()) {
                if (item.getBoolFlag().equals(boolFlag)) {
                    return item;
                }
            }
        }
        return null;
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
    public YesOrNotEnum parseToEnum(String originValue) {
        if (ObjectUtil.isEmpty(originValue)) {
            return null;
        }
        for (YesOrNotEnum value : YesOrNotEnum.values()) {
            if (value.boolFlag.equals(Convert.toBool(originValue))) {
                return value;
            }
        }
        return null;
    }

}
