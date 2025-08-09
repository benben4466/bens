package cn.ibenbeni.bens.rule.enums.permission;

import cn.hutool.core.util.ArrayUtil;
import cn.ibenbeni.bens.rule.base.ReadableEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.rule.exception.enums.DataScopeExceptionEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * 数据范围类型枚举，数据范围的值越小，数据权限越小
 *
 * @author benben
 * @date 2025/5/25  下午4:12
 */
@Getter
public enum DataScopeTypeEnum implements ReadableEnum<DataScopeTypeEnum> {

    /**
     * 仅本人数据
     */
    SELF(10, "仅本人数据"),

    /**
     * 本部门数据
     */
    DEPT_ONLY(20, "本部门数据"),

    /**
     * 本部门及以下数据
     */
    DEPT_WITH_CHILD(30, "本部门及以下数据"),

    /**
     * 本公司及以下数据
     */
    COMPANY_WITH_CHILD(31, "本公司及以下数据"),

    /**
     * 指定部门数据
     */
    DEPT_CUSTOM(40, "指定部门数据"),

    /**
     * 全部数据
     */
    ALL(50, "全部数据"),
    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(DataScopeTypeEnum::getCode).toArray(Integer[]::new);

    private final Integer code;

    private final String message;

    DataScopeTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static DataScopeTypeEnum codeToEnum(Integer code) {
        if (null != code) {
            for (DataScopeTypeEnum dataScopeTypeEnum : DataScopeTypeEnum.values()) {
                if (dataScopeTypeEnum.getCode().equals(code)) {
                    return dataScopeTypeEnum;
                }
            }
        }
        throw new ServiceException(DataScopeExceptionEnum.DATA_SCOPE_ERROR);
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
    public DataScopeTypeEnum parseToEnum(String code) {
        return ArrayUtil.firstMatch(item -> item.getCode().equals(Integer.valueOf(code)), values());
    }

    @Override
    public Object[] compareValueArray() {
        return ARRAYS;
    }

}
