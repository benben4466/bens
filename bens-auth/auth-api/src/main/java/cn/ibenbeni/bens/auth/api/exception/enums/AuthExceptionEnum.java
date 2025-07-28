package cn.ibenbeni.bens.auth.api.exception.enums;

import cn.ibenbeni.bens.auth.api.constants.AuthConstants;
import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * auth鉴权模块的常量
 *
 * @author benben
 * @date 2025/5/20  下午2:31
 */
@Getter
public enum AuthExceptionEnum implements AbstractExceptionEnum {

    /**
     * 会话过期或超时，token过期，都属于这种异常，提示用户从新登录
     */
    AUTH_EXPIRED_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "01", "当前登录会话过期，请重新登录"),

    /**
     * 登陆时，账号或密码为空
     */
    PARAM_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "03", "登陆失败，账号或密码参数为空"),

    /**
     * 账号或密码错误
     */
    USERNAME_PASSWORD_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "04", "账号或密码错误"),

    /**
     * 用户状态异常，可能被禁用可能被冻结，用StrUtil.format()格式化
     */
    USER_STATUS_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "05", "当前用户被{}，请检查用户状态是否正常"),

    /**
     * 登陆失败，账号参数为空
     */
    ACCOUNT_IS_BLANK(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "06", "登陆失败，账号参数为空"),

    /**
     * 获取token失败
     */
    TOKEN_GET_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "07", "获取token失败，请检查header和param中是否传递了用户token"),

    /**
     * 密码重试次数过多，帐号被锁定
     */
    LOGIN_LOCKED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "18", "账号或密码错误!"),

    /**
     * Token解析失败
     */
    TOKEN_PARSE_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "07", "Token解析失败"),

    /**
     * 权限编码参数为空
     */
    PERMISSION_CODE_PARAM_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "07", "权限编码参数为空"),

    /**
     * 权限校验未通过
     */
    PERMISSION_VALIDATE_NOT_PASS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "07", "权限校验未通过"),

    /**
     * 用户未登录
     */
    USER_NOT_LOGIN(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + AuthConstants.AUTH_EXCEPTION_STEP_CODE + "07", "用户未登录"),
    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    AuthExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
