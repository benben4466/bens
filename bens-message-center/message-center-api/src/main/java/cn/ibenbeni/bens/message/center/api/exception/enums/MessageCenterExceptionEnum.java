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
