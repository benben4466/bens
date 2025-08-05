package cn.ibenbeni.bens.sys.modular.role.enums;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * 角色绑定的限制类型
 *
 * @author: benben
 * @time: 2025/6/10 上午10:28
 */
@Getter
public enum RoleLimitTypeEnum implements ReadableEnum<RoleLimitTypeEnum> {

    /**
     * 菜单
     */
    MENU(1, "菜单"),

    /**
     * 菜单功能
     */
    MENU_OPTIONS(2, "菜单功能");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(RoleLimitTypeEnum::getCode).toArray(Integer[]::new);

    private final Integer code;

    private final String message;

    RoleLimitTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
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
    public RoleLimitTypeEnum parseToEnum(String originValue) {
        if (ObjectUtil.isEmpty(originValue)) {
            return null;
        }
        for (RoleLimitTypeEnum value : RoleLimitTypeEnum.values()) {
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
