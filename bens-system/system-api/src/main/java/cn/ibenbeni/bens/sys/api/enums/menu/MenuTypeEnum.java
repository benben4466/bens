package cn.ibenbeni.bens.sys.api.enums.menu;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * 菜单类型
 *
 * @author: benben
 * @time: 2025/6/1 下午3:18
 */
@Getter
public enum MenuTypeEnum implements ReadableEnum<MenuTypeEnum> {

    DIRECTORY(1, "目录"),

    MENU(2, "菜单"),

    BUTTON(3, "按钮"),

    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(MenuTypeEnum::getCode).toArray(Integer[]::new);

    private final Integer code;

    private final String message;

    MenuTypeEnum(Integer code, String message) {
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
    public MenuTypeEnum parseToEnum(String originValue) {
        if (ObjectUtil.isEmpty(originValue)) {
            return null;
        }
        for (MenuTypeEnum value : MenuTypeEnum.values()) {
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
