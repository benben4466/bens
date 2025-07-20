package cn.ibenbeni.bens.sys.modular.role.enums.exception;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 系统角色异常相关枚举
 *
 * @author benben
 * @date 2025/5/3  下午11:01
 */
@Getter
public enum SysRoleExceptionEnum implements AbstractExceptionEnum {

    /**
     * 查询结果不存在
     */
    SYS_ROLE_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10001", "查询结果不存在"),

    /**
     * 角色编码不能被修改
     */
    SUPER_ADMIN_ROLE_CODE_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10002", "角色编码不能被修改"),

    /**
     * 超级管理员不能被删除
     */
    SYSTEM_ROLE_CANT_DELETE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10003", "系统角色不能被删除"),

    /**
     * 非管理员，只能删除自己公司的角色
     */
    DEL_PERMISSION_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10004", "非管理员，只能删除自己公司的角色"),

    /**
     * 非管理员，不能添加系统角色
     */
    ROLE_TYPE_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10005", "非管理员，不能添加系统角色"),

    /**
     * 非管理员，不能添加其他公司角色
     */
    ROLE_COMPANY_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10006", "非管理员，不能添加其他公司角色"),

    /**
     * 不允许创建超级管理员
     */
    ROLE_ADMIN_CODE_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10007", "不允许创建超级管理员"),

    /**
     * 已经存在名为【{}】的角色
     */
    ROLE_NAME_DUPLICATE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10008", "已经存在名为【{}】的角色"),

    /**
     * 已经存在编码为【{}】的角色
     */
    ROLE_CODE_DUPLICATE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10009", "已经存在编码为【{}】的角色"),

    /**
     * 角色编码缺失
     */
    ROLE_CODE_LACK(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10010", "角色编码缺失"),

    /**
     * 不能操作类型为系统内置的角色
     */
    ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10011", "不能操作类型为系统内置的角色"),

    /**
     * 角色被禁用
     */
    ROLE_DISABLED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10011", "名字为【{}】的角色已被禁用"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SysRoleExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
