package cn.ibenbeni.bens.sys.api.enums.role;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * 角色类型
 *
 * @author benben
 * @date 2025/5/3  下午10:54
 */
@Getter
public enum RoleTypeEnum implements ReadableEnum<RoleTypeEnum> {

    SYSTEM(10, "内置角色"),

    CUSTOM(20, "自定义角色"),

    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(RoleTypeEnum::getCode).toArray(Integer[]::new);

    private final Integer code;

    private final String name;

    RoleTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Object getKey() {
        return code;
    }

    @Override
    public Object getName() {
        return name;
    }

    @Override
    public RoleTypeEnum parseToEnum(String originValue) {
        if (ObjectUtil.isEmpty(originValue)) {
            return null;
        }
        for (RoleTypeEnum value : RoleTypeEnum.values()) {
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
