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

    /**
     * 租户名称为空
     */
    TENANT_NAME_BLANK(RuleConstants.BUSINESS_ERROR_TYPE_CODE + TenantConstants.TENANT_EXCEPTION_STEP_CODE + "02", "租户名称为空"),

    /**
     * 租户域名为空
     */
    TENANT_WEBSITE_BLANK(RuleConstants.BUSINESS_ERROR_TYPE_CODE + TenantConstants.TENANT_EXCEPTION_STEP_CODE + "03", "租户域名为空"),

    /**
     * 租户域名已存在
     */
    TENANT_WEBSITE_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + TenantConstants.TENANT_EXCEPTION_STEP_CODE + "04", "租户域名已存在"),

    /**
     * 租户已存在
     */
    TENANT_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + TenantConstants.TENANT_EXCEPTION_STEP_CODE + "05", "租户已存在"),

    /**
     * 租户套餐不存在
     */
    TENANT_PACKAGE_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + TenantConstants.TENANT_EXCEPTION_STEP_CODE + "100", "租户套餐不存在"),

    /**
     * 租户套餐已存在
     */
    TENANT_PACKAGE_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + TenantConstants.TENANT_EXCEPTION_STEP_CODE + "101", "租户套餐已存在"),

    /**
     * 租户套餐请求参数错误
     */
    TENANT_PACKAGE_PARAM_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + TenantConstants.TENANT_EXCEPTION_STEP_CODE + "102", "租户套餐请求参数错误, 提示: {}"),

    /**
     * 租户套餐已被禁用
     */
    TENANT_PACKAGE_DISABLE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + TenantConstants.TENANT_EXCEPTION_STEP_CODE + "103", "租户套餐已被禁用"),

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
