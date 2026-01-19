package cn.ibenbeni.bens.message.center.api.exception.enums;

import cn.ibenbeni.bens.message.center.api.constants.MessageCenterConstants;
import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageCenterExceptionEnum implements AbstractExceptionEnum {

    // region 通知公告

    /**
     * 通知公告不存在
     */
    NOTICE_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "01", "通知公告不存在"),

    // endregion

    // region 站内信方法

    /**
     * 站内信模板不存在
     */
    NOTIFY_TEMPLATE_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "50", "站内信模板({})不存在"),

    /**
     * 站内信模板参数缺失
     */
    NOTIFY_SEND_TEMPLATE_PARAM_MISS(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "51", "模板参数({})缺失"),

    // region 消息模板
    /**
     * 消息模板不存在
     */
    TEMPLATE_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "60", "消息模板不存在"),
    /**
     * 消息模板编码重复
     */
    TEMPLATE_CODE_DUPLICATE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "61", "消息模板编码已存在"),
    /**
     * 消息模板内容不存在
     */
    TEMPLATE_CONTENT_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "62", "消息模板内容不存在"),
    /**
     * 消息模板内容重复（模板ID与渠道类型唯一）
     */
    TEMPLATE_CONTENT_DUPLICATE(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "63", "同一模板下该渠道类型的内容已存在"),
    // endregion

    // region 消息接入层异常

    /**
     * 消息模板未启用
     */
    TEMPLATE_NOT_ENABLED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "70", "消息模板未启用"),

    /**
     * 消息模板未审核通过
     */
    TEMPLATE_NOT_APPROVED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "71", "消息模板未审核通过"),

    /**
     * 消息模板参数缺失
     */
    TEMPLATE_PARAM_MISS(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "72", "模板参数({})缺失"),

    /**
     * 接收者信息缺失
     */
    RECIPIENT_INFO_MISS(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "73", "接收者信息缺失"),

    /**
     * 模板不支持该渠道
     */
    TEMPLATE_CHANNEL_NOT_SUPPORT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "74", "消息模板不支持该渠道类型"),

    /**
     * 消息投递失败
     */
    MESSAGE_QUEUE_SEND_FAIL(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "75", "消息投递失败"),

    // endregion

    // region 消息处理层异常

    /**
     * 敏感词检测不通过
     */
    SENSITIVE_WORD_DETECTED(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "80", "消息内容包含敏感词"),

    /**
     * 渠道配置不存在
     */
    CHANNEL_CONFIG_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "81", "渠道配置不存在"),

    /**
     * 渠道发送失败
     */
    CHANNEL_SEND_FAIL(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "82", "渠道发送失败: {}"),

    /**
     * 发送记录不存在
     */
    SEND_RECORD_NOT_EXIST(RuleConstants.BUSINESS_ERROR_TYPE_CODE + MessageCenterConstants.MESSAGE_CENTER_EXCEPTION_STEP_CODE + "83", "发送记录不存在"),

    // endregion

    // endregion

    ;

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

}
