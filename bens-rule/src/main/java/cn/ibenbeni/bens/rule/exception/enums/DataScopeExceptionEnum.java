package cn.ibenbeni.bens.rule.exception.enums;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 数据范围的基础异常枚举
 *
 * @author benben
 * @date 2025/5/25  下午4:14
 */
@Getter
public enum DataScopeExceptionEnum implements AbstractExceptionEnum {

    /**
     * 数据范围类型转化异常
     */
    DATA_SCOPE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + "10001", "数据范围类型转化异常");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DataScopeExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
