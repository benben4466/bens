package cn.ibenbeni.bens.message.center.api.enums.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
