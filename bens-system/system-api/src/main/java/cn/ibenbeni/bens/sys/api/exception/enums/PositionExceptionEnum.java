package cn.ibenbeni.bens.sys.api.exception.enums;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 职位信息错误异常枚举
 *
 * @author: benben
 * @time: 2025/7/12 下午2:10
 */
@Getter
public enum PositionExceptionEnum implements AbstractExceptionEnum {

    /**
     * 职位不存在
     */
    POSITION_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "30001", "职位不存在"),

    /**
     * 职位名称重复
     */
    POSITION_NAME_DUPLICATE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "30002", "职位名称重复"),

    /**
     * 职位编码重复
     */
    POSITION_CODE_DUPLICATE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "30003", "职位编码重复"),

    /**
     * 职位被禁用
     */
    POSITION_DISABLE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + "30004", "职位被禁用"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    PositionExceptionEnum(String errorCode, String userTip) {
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
