package cn.ibenbeni.bens.config.api.exception.enums;

import cn.ibenbeni.bens.config.api.constants.ConfigConstants;
import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 系统配置表相关的异常枚举
 *
 * @author: benben
 * @time: 2025/6/18 下午10:54
 */
@Getter
public enum ConfigExceptionEnum implements AbstractExceptionEnum {

    /**
     * 数据库操作未知异常
     */
    DAO_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "01", "配置表信息操作异常"),

    /**
     * 系统配置表不存在该配置
     * <p>
     * 使用时候，用StrUtil.format()将配置名称带上
     */
    CONFIG_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "02", "系统配置表不存在该配置，配置名称：{}，系统将使用默认配置"),

    /**
     * 系统配置表获取值时，强转类型异常
     * <p>
     * 使用时候，用StrUtil.format()将配置名称带上
     */
    CONVERT_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "03", "获取系统配置值时，强转类型异常，配置名称：{}，配置值：{}，转化类型：{}"),

    /**
     * 获取不到application.yml中的数据库配置
     */
    DB_CONFIG_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "04", "获取主数据源失败,无法初始化系统配置表"),

    /**
     * 初始化系统配置表失败，找不到com.mysql.cj.jdbc.Driver驱动类
     */
    CLASS_NOT_FOUND_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "06", "初始化系统配置表失败，找不到com.mysql.cj.jdbc.Driver驱动类"),

    /**
     * 初始化系统配置表失败，执行查询语句失败
     */
    CONFIG_SQL_EXE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "07", "初始化系统配置表失败，执行查询语句失败"),

    /**
     * 系统参数配置编码重复
     */
    CONFIG_CODE_REPEAT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "08", "系统参数配置编码重复，请检查code参数"),

    /**
     * 删除失败，不能删除系统参数
     */
    CONFIG_SYS_CAN_NOT_DELETE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "09", "删除失败，不能删除系统参数"),

    /**
     * 配置容器是空，请先初始化配置容器
     */
    CONFIG_CONTAINER_IS_NULL(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "10", "配置容器为空，请先初始化配置容器，请调用ConfigContext.setConfigApi()初始化"),

    /**
     * 初始化配置失败，参数为空
     */
    CONFIG_INIT_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "11", "初始化配置失败，参数为空"),

    /**
     * 初始化配置失败，系统已经初始化
     */
    CONFIG_INIT_ALREADY(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "12", "初始化配置失败，系统配置已经初始化"),

    /**
     * 系统配置类型不存在
     */
    CONFIG_TYPE_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "13", "系统配置类型表不存在该配置类型"),

    /**
     * 删除失败，不能删除系统配置类型
     */
    CONFIG_TYPE_SYS_CAN_NOT_DELETE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "14", "删除失败，不能删除系统配置类型"),

    /**
     * 系统配置类型编码重复
     */
    CONFIG_TYPE_CODE_DUPLICATE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "13", "系统配置类型编码重复"),

    /**
     * 系统配置类型参数错误
     */
    CONFIG_TYPE_PARAM_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "13", "系统配置类型参数错误"),

    /**
     * 系统参数类型配置不允许删除
     */
    CONFIG_TYPE_CAN_NOT_DELETE_SYSTEM_TYPE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "09", "系统参数类型配置不允许删除"),

    /**
     * 系统编码重复
     */
    CONFIG_CODE_DUPLICATE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "13", "系统编码重复"),


    /**
     * 系统配置不允许删除
     */
    CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "09", "系统配置不允许删除"),

    /**
     * 系统类型下存在参数,不允许删除
     */
    DICT_TYPE_HAS_CHILDREN(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "09", "系统类型下存在参数,不允许删除"),

    /**
     * 获取参数配置失败,原因:不允许获取不可见配置
     */
    CONFIG_GET_VALUE_ERROR_IF_VISIBLE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + ConfigConstants.CONFIG_EXCEPTION_STEP_CODE + "09", "获取参数配置失败,原因:不允许获取不可见配置"),

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    ConfigExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
