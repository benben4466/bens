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
