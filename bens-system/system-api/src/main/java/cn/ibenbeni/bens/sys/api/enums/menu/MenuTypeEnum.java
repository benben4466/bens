package cn.ibenbeni.bens.sys.api.enums.menu;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;

/**
 * 菜单类型
 * <p>10-后台菜单，20-纯前台路由界面，30-内部链接，40-外部链接，50-应用设计</p>
 *
 * @author: benben
 * @time: 2025/6/1 下午3:18
 */
@Getter
public enum MenuTypeEnum implements ReadableEnum<MenuTypeEnum> {

    /**
     * 后台菜单
     */
    BACKEND_MENU(10, "后台菜单"),

    /**
     * 纯前台路由界面
     */
    FRONT_VUE(20, "纯前台路由界面"),

    /**
     * 内部链接
     */
    INNER_URL(30, "内部链接"),

    /**
     * 外部链接
     */
    OUT_URL(40, "外部链接"),

    /**
     * 应用设计菜单，基于应用设计生成的低代码界面
     */
    APP_DESIGN(50, "应用设计"),
    ;

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

}
