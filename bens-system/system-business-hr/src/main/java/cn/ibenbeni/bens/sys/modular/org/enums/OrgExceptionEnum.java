package cn.ibenbeni.bens.sys.modular.org.enums;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 组织机构信息异常相关枚举
 *
 * @author benben
 */
@Getter
public enum OrgExceptionEnum implements AbstractExceptionEnum {

    /**
     * 查询结果不存在
     */
    HR_ORGANIZATION_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10001", "查询结果不存在"),

    /**
     * 删除机构失败，该机构下有绑定员工
     */
    DELETE_ORGANIZATION_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10002", "删除机构失败，该机构下有绑定员工"),

    /**
     * 用户没有该组织机构的权限，无法切换组织机构
     */
    UPDATE_LOGIN_USER_ORG_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10003", "用户没有该组织机构的权限，无法切换组织机构"),

    /**
     * 组织机构编码已存在
     */
    HR_ORGANIZATION_ORG_CODE_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10004", "组织机构编码已存在"),
    ;


    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    OrgExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
