package cn.ibenbeni.bens.tenant.api.exception.enums;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.tenant.api.constants.TenantConstants;
import lombok.Getter;

/**
 * 租户异常枚举
 *
 * @author: benben
 * @time: 2025/6/27 上午11:12
 */
@Getter
public enum TenantExceptionEnum implements AbstractExceptionEnum {

    /**
     * 请求中租户不存在
     */
    TENANT_REQUEST_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + TenantConstants.TENANT_EXCEPTION_STEP_CODE + "01", "请求中租户不存在"),
    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    TenantExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
