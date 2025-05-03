package cn.ibenbeni.bens.sys.api.exception.enums;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 用户异常枚举
 *
 * @author benben
 * @date 2025/5/3  下午7:22
 */
@Getter
public enum UserExceptionEnum implements AbstractExceptionEnum {

    /**
     * 请求状态值为空
     */
    REQUEST_USER_STATUS_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10001", "请求状态值为空"),

    /**
     * 请求状值为非正确状态值
     */
    REQUEST_USER_STATUS_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10002", "请求状态值不合法，用户状态参数不合法，参数值：{}"),

    /**
     * 无法操作，只有超级管理员可以重置密码！
     */
    RESET_PASSWORD_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10003", "无法操作，只有超级管理员可以重置密码！"),

    /**
     * 无法操作，只有超级管理员可以踢下线用户
     */
    KICK_OFF_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10004", "无法操作，只有超级管理员可以踢下线用户"),

    /**
     * 获取用户失败，参数缺失：用户token
     */
    TOKEN_EMPTY_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "10005", "获取用户失败，参数缺失：用户token");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    UserExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
