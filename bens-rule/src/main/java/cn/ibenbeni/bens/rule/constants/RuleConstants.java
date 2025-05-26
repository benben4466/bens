package cn.ibenbeni.bens.rule.constants;

/**
 * 规则模块的常量
 *
 * @author benben
 * @date 2025/4/18  上午11:29
 */
public interface RuleConstants {

    /**
     * 用户端操作异常的错误码分类编号
     */
    String USER_OPERATION_ERROR_TYPE_CODE = "A";

    /**
     * 业务执行异常的错误码分类编号
     */
    String BUSINESS_ERROR_TYPE_CODE = "B";

    /**
     * 请求成功的返回码
     */
    String SUCCESS_CODE = "00000";

    /**
     * 请求成功的返回信息
     */
    String SUCCESS_MESSAGE = "请求成功";

    /**
     * 规则模块的名称
     */
    String RULE_MODULE_NAME = "bens-rule";

    /**
     * 异常枚举的步进值
     */
    String RULE_EXCEPTION_STEP_CODE = "01";

    /**
     * 中文的多语言类型编码
     */
    String CHINESE_TRAN_LANGUAGE_CODE = "chinese";

}
