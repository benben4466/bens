package cn.ibenbeni.bens.dict.api.exception.enums;

import cn.ibenbeni.bens.dict.api.constants.DictConstants;
import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 字典模块相关异常枚举
 *
 * @author: benben
 * @time: 2025/6/14 上午10:36
 */
@Getter
public enum DictExceptionEnum implements AbstractExceptionEnum {

    /**
     * 同类字典类型下，字典名称重复
     */
    DICT_NAME_REPEAT(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "02", "同类字典类型下，字典名称重复，字典类型：{}，字典名称：{}"),

    /**
     * 父级id不存在，输入的父级id不合理
     */
    PARENT_DICT_NOT_EXISTED(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "03", "父级ID不存在，输入的父级ID不合理，父级ID：{}"),

    /**
     * 字典不存在
     */
    DICT_NOT_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "04", "字典不存在，字典id：{}"),

    /**
     * 字典状态错误
     */
    DICT_NOT_ENABLE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "05", "字典状态错误"),

    /**
     * 字典类型状态错误
     */
    DICT_TYPE_NOT_ENABLE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "05", "字典类型状态错误"),

    /**
     * 字典类型编码重复
     */
    DICT_TYPE_CODE_DUPLICATE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "06", "字典类型编码重复，字典类型编码：{}"),

    /**
     * 系统字典不允许操作
     */
    SYSTEM_DICT_NOT_ALLOW_OPERATION(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "07", "系统字典不允许操作，如需操作请联系超级管理员！"),

    /**
     * 字典类型名称重复
     */
    DICT_TYPE_NAME_DUPLICATE(RuleConstants.USER_OPERATION_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "06", "字典类型名称重复，字典类型名称：{}"),

    /**
     * 字典类型不存在
     */
    DICT_TYPE_NOT_EXISTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "04", "字典类型不存在"),

    /**
     * 字典值重复
     */
    DICT_VALUE_DUPLICATE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "04", "字典值重复"),

    /**
     * 无法删除,该字典类型还有字典数据
     */
    DICT_TYPE_HAS_CHILDREN(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DictConstants.DICT_EXCEPTION_STEP_CODE + "04", "无法删除,该字典类型还有字典数据"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DictExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
