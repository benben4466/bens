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

    /**
     * 根据类型代码获取枚举
     */
    public static MsgPushChannelTypeEnum fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (MsgPushChannelTypeEnum channelType : values()) {
            if (channelType.getType().equals(code)) {
                return channelType;
            }
        }
        throw new IllegalArgumentException("Unknown channel type code: " + code);
    }

}
