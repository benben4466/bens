package cn.ibenbeni.bens.message.center.api.enums.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息中心-接收人类型
 */
@Getter
@AllArgsConstructor
public enum MsgRecipientTypeEnum {

    MOBILE_PHONE(10, "移动手机号"),
    EMAIL_ACCOUNT(20, "邮件账号"),
    USER_ID(30, "用户ID"),
    ;

    private final Integer type;
    private final String desc;

}
