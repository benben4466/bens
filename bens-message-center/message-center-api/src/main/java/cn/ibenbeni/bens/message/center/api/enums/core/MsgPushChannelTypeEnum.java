package cn.ibenbeni.bens.message.center.api.enums.core;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
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

    @JsonValue // 存数据库用
    @EnumValue // 返回 JSON 用
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
