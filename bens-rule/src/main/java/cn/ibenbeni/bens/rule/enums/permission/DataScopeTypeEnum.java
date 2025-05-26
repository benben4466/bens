package cn.ibenbeni.bens.rule.enums.permission;

import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.rule.exception.enums.DataScopeExceptionEnum;
import lombok.Getter;

/**
 * 数据范围类型枚举，数据范围的值越小，数据权限越小
 *
 * @author benben
 * @date 2025/5/25  下午4:12
 */
@Getter
public enum DataScopeTypeEnum {
    /**
     * 本部门及以下数据
     */
    DEPT_WITH_CHILD(30, "本部门及以下数据"),
    ;

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

}
