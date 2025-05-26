package cn.ibenbeni.bens.validator.api.exception.enums;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.validator.api.constants.ValidatorConstants;
import lombok.Getter;

/**
 * 参数校验错误
 *
 * @author benben
 * @date 2025/5/20  下午3:51
 */
@Getter
public enum ValidatorExceptionEnum implements AbstractExceptionEnum {

    /**
     * 验证码为空
     */
    CAPTCHA_EMPTY(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "08", "验证码参数不能为空"),

    /**
     * 验证码错误
     */
    CAPTCHA_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "09", "验证码错误"),

    /**
     * 拖拽验证码错误
     */
    DRAG_CAPTCHA_ERROR(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + ValidatorConstants.VALIDATOR_EXCEPTION_STEP_CODE + "11", "拖拽验证码错误");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    ValidatorExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
