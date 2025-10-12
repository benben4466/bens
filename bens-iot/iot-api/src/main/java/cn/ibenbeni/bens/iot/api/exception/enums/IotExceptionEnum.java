package cn.ibenbeni.bens.iot.api.exception.enums;

import cn.ibenbeni.bens.iot.api.constants.IotConstants;
import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * IOT模块的异常枚举
 */
@Getter
public enum IotExceptionEnum implements AbstractExceptionEnum {

    /**
     * 产品分类不存在
     */
    PRODUCT_CATEGORY_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "01", "产品分类不存在"),

    /**
     * 内置产品分类不允许删除
     */
    PRODUCT_CATEGORY_NOT_ALLOW_DELETE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "02", "内置产品分类不允许删除"),

    /**
     * 内置产品分类的内置标识不允许修改
     */
    PRODUCT_CATEGORY_NOT_ALLOW_UPDATE_SYS_FLAG(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "03", "内置标识不允许修改"),

    /**
     * 产品不存在
     */
    PRODUCT_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "04", "产品不存在"),

    /**
     * 内置产品不允许删除
     */
    PRODUCT_NOT_ALLOW_DELETE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "05", "内置产品不允许删除"),

    /**
     * 设备分组不存在
     */
    DEVICE_GROUP_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "300", "设备分组不存在"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    IotExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
