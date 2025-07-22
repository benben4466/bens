package cn.ibenbeni.bens.jwt.api.exception.enums;

import cn.ibenbeni.bens.jwt.api.constants.JwtConstants;
import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * JWT异常枚举码
 */
@Getter
public enum JwtExceptionEnum implements AbstractExceptionEnum {

    /**
     * JWT解析异常
     */
    JWT_PARSE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + JwtConstants.JWT_EXCEPTION_STEP_CODE + "01", "JWT解析错误"),

    /**
     * JWT已过期
     */
    JWT_EXPIRED_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + JwtConstants.JWT_EXCEPTION_STEP_CODE + "02", "JWT已过期"),

    /**
     * JWT参数为空
     */
    JWT_PARAM_EMPTY(RuleConstants.BUSINESS_ERROR_TYPE_CODE + JwtConstants.JWT_EXCEPTION_STEP_CODE + "03", "JWT解析时，秘钥或过期时间为空"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    JwtExceptionEnum(String errorCode, String userTip) {
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
