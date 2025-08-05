package cn.ibenbeni.bens.sys.api.enums.menu;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * 组织类型枚举
 *
 * @author: benben
 * @time: 2025/7/7 下午3:25
 */
@Getter
public enum OrganizationTypeEnum implements ReadableEnum<OrganizationTypeEnum> {

    COMPANY(1, "公司"),

    DEPARTMENT(2, "部门"),

    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(OrganizationTypeEnum::getCode).toArray(Integer[]::new);

    private final Integer code;

    private final String message;

    OrganizationTypeEnum(Integer code, String message) {
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
    public OrganizationTypeEnum parseToEnum(String orgCode) {
        if (StrUtil.isBlank(orgCode) || !NumberUtil.isNumber(orgCode)) {
            return null;
        }

        for (OrganizationTypeEnum value : values()) {
            if (value.code.equals(Integer.valueOf(orgCode))) {
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
