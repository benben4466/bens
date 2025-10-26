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
     * 产品下存在设备,不允许删除
     */
    PRODUCT_DELETE_FAIL_HAS_DEVICE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "06", "产品下存在设备,不允许删除"),

    /**
     * 设备分组不存在
     */
    DEVICE_GROUP_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "300", "设备分组不存在"),

    /**
     * 设备不存在
     */
    DEVICE_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "400", "设备不存在"),

    /**
     * 设备SN已存在
     */
    DEVICE_SN_NOT_EXISTS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "401", "设备SN已存在"),

    /**
     * 设备名称在同一产品下必须唯一
     */
    DEVICE_NAME_EXISTS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "402", "设备名称在同一产品下必须唯一"),

    /**
     * 物模型模标识符已存在
     */
    THING_MODEL_TEMPLATE_IDENTIFIER_EXISTS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "500", "物模型模标识符已存在"),

    /**
     * 物模型模名称已存在
     */
    THING_MODEL_TEMPLATE_NAME_EXISTS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "501", "物模型模名称已存在"),

    /**
     * 物模型不存在
     */
    THING_MODEL_TEMPLATE_NOT_EXISTS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "502", "物模型模板不存在"),

    /**
     * 内置物模型禁止删除
     */
    THING_MODEL_TEMPLATE_SYS_PROHIBIT_DELETE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "503", "内置物模型禁止删除"),

    /**
     * 物模型不存在
     */
    THING_MODEL_NOT_EXISTS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "503", "物模型不存在"),

    /**
     * 物模型标识符已存在
     */
    THING_MODEL_IDENTIFIER_EXISTS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "504", "物模型标识符已存在"),

    /**
     * 物模型名称已存在
     */
    THING_MODEL_NAME_EXISTS(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "505", "物模型名称已存在"),

    /**
     * 产品状是发布状态,不允许操作物模型
     */
    PRODUCT_STATUS_NOT_ALLOW_THING_MODEL(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "506", "产品状是发布状态,不允许操作物模型"),

    /**
     * 下行设备消息失败, 原因: 设备未连接网关
     */
    DEVICE_DOWNSTREAM_FAILED_SERVER_ID_NULL(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + IotConstants.IOT_MODULE_NAME + "507", "下行设备消息失败, 原因: 设备未连接网关"),


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
