package cn.ibenbeni.bens.sys.modular.role.enums;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * 节点类型
 * <p>1-应用，2-菜单，3-功能，-1-所有权限</p>
 * <p>本程序无应用，因此弃用</p>
 *
 * @author: benben
 * @time: 2025/6/10 上午10:04
 */
@Getter
public enum PermissionNodeTypeEnum implements ReadableEnum<PermissionNodeTypeEnum> {


    /**
     * 应用
     */
    APP(1, "应用"),

    /**
     * 菜单
     */
    MENU(2, "菜单"),

    /**
     * 功能
     */
    OPTIONS(3, "功能"),

    /**
     * 所有权限
     */
    TOTAL(-1, "所有权限"),
    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(PermissionNodeTypeEnum::getCode).toArray(Integer[]::new);

    private final Integer code;

    private final String message;

    PermissionNodeTypeEnum(Integer code, String message) {
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
    public PermissionNodeTypeEnum parseToEnum(String originValue) {
        if (ObjectUtil.isEmpty(originValue)) {
            return null;
        }
        for (PermissionNodeTypeEnum value : PermissionNodeTypeEnum.values()) {
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
