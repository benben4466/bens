package cn.ibenbeni.bens.permission.api.exception.enums;

import cn.ibenbeni.bens.permission.api.constants.PermissionConstants;
import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限异常枚举
 */
@Getter
@AllArgsConstructor
public enum PermissionExceptionEnum implements AbstractExceptionEnum {

    /**
     * 数据权限为空
     */
    PERMISSION_DATA_EMPTY(RuleConstants.BUSINESS_ERROR_TYPE_CODE + PermissionConstants.PERMISSION_EXCEPTION_STEP_CODE + "01", "数据权限为空,用户账号:{},表名:{},表别名:{}"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getUserTip() {
        return userTip;
    }

}
