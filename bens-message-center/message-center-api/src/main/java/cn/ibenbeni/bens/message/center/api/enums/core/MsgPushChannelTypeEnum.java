package cn.ibenbeni.bens.message.center.api.enums.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息推送渠道类型
 */
@Getter
@AllArgsConstructor
public enum MsgPushChannelTypeEnum {
    LETTER(10, "站内信"),
    SMS(20, "短信"),
    EMAIL(30, "邮件"),
    PUSH(40, "推送");

    private final Integer type;
    private final String desc;

}
