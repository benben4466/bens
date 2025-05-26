package cn.ibenbeni.bens.security.api.exception.enums;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.security.api.constants.SecurityConstants;
import lombok.Getter;

/**
 * 安全模块异常枚举
 *
 * @author benben
 * @date 2025/5/20  下午4:30
 */
@Getter
public enum SecurityExceptionEnum implements AbstractExceptionEnum {

    /**
     * 生成验证码错误
     */
    CAPTCHA_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SecurityConstants.SECURITY_EXCEPTION_STEP_CODE + "01", "生成验证码错误"),

    /**
     * 验证码过期，请从新生成验证码
     */
    CAPTCHA_INVALID_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SecurityConstants.SECURITY_EXCEPTION_STEP_CODE + "02", "验证码过期，请从新生成验证码");


    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    SecurityExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
