package cn.ibenbeni.bens.sys.api.exception.enums;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 组织机构异常枚举
 *
 * @author: benben
 * @time: 2025/7/7 下午8:50
 */
@Getter
public enum OrganizationExceptionEnum implements AbstractExceptionEnum {

    /**
     * 不能设置自己为父部门
     */
    ORG_PARENT_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "20001", "不能设置自己为父组织"),

    /**
     * 组织不存在
     */
    ORG_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "20002", "组织不存在"),

    /**
     * 不能设置自己的子部门为父组织
     */
    ORG_PARENT_IS_CHILD(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "20003", "不能设置自己的子组织为父组织"),

    /**
     * 组织名称已存在
     */
    ORG_NAME_DUPLICATE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "20004", "组织名称已存在"),

    /**
     * 组织编码已存在
     */
    ORG_CODE_DUPLICATE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "20005", "组织编码已存在"),

    /**
     * 组织已存在
     */
    ORG_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "20006", "组织已存在"),

    /**
     * 存在子组织, 无法删除
     */
    ORG_EXITS_CHILDREN(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "20007", "存在子组织,无法删除"),

    /**
     * 组织未启用
     */
    ORG_NOT_ENABLE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "20008", "组织({})未启用,禁止选择"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    OrganizationExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
