package cn.ibenbeni.bens.sys.api.enums.datascope;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import lombok.Getter;

/**
 * 数据权限枚举
 */
@Getter
public enum DataScopeEnum implements ReadableEnum<DataScopeEnum> {

    SELF(10, "仅本人数据"),
    DEPT_ONLY(20, "本部门数据"),
    DEPT_AND_CHILD(30, "本部门及以下数据"),
    DEPT_CUSTOM(40, "指定部门数据"),
    ALL(50, "全部数据"),

    ;

    private final Integer code;

    private final String message;

    DataScopeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Object getKey() {
        return code;
    }

    @Override
    public Object getName() {
        return message;
    }

    @Override
    public DataScopeEnum parseToEnum(String code) {
        if (ObjectUtil.isEmpty(code)) {
            return null;
        }
        for (DataScopeEnum value : values()) {
            if (value.code.equals(Convert.toInt(code))) {
                return value;
            }
        }
        return null;
    }

}
