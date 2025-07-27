package cn.ibenbeni.bens.resource.api.exception.enums;

import cn.ibenbeni.bens.resource.api.constants.ResourceConstants;
import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 资源模块异常枚举
 */
@Getter
public enum ResourceExceptionEnum implements AbstractExceptionEnum {

    /**
     * 资源不存在
     */
    RESOURCE_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ResourceConstants.RESOURCE_MODULE_NAME + "01", "{}资源不存在"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    ResourceExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
