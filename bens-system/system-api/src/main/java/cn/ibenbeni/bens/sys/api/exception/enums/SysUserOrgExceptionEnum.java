package cn.ibenbeni.bens.sys.api.exception.enums;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 用户组织机构关联异常相关枚举
 *
 * @author: benben
 * @time: 2025/7/12 上午9:50
 */
@Getter
public enum SysUserOrgExceptionEnum implements AbstractExceptionEnum {

    /**
     * 用户组织关系为空
     */
    USER_ORG_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10006", "用户组织关系为空"),

    /**
     * 用户组织关系为空
     */
    MAIN_FLAG_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10006", "用户组织关系为空"),

    /**
     * 用户已在该组织任职该职位
     */
    USER_POSITION_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10006", "用户已任职该职位"),

    /**
     * 用户组织关系不存在
     */
    USER_ORG_NOT_EXIST(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10006", "用户组织关系不存在"),

    /**
     * 用户组织关系参数缺失
     */
    PARAM_MISSING(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10006", "用户组织关系参数缺失"),

    /**
     * 组织下存在关联的用户,不允许删除
     */
    ORG_REMOVE_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10006", "组织下存在关联用户,不允许删除"),

    /**
     * 该职位下存在关联用户,不允许删除
     */
    POSITION_REMOVE_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10006", "该职位下存在关联用户,不允许删除"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysUserOrgExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getUserTip() {
        return userTip;
    }

}
