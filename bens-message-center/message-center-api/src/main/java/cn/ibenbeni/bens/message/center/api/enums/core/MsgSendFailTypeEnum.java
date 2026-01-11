package cn.ibenbeni.bens.message.center.api.enums.core;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息发送失败类型枚举
 */
@Getter
@AllArgsConstructor
public enum MsgSendFailTypeEnum {

    MSG_TEMPLATE_NOT_EXIST(0, "消息模板/渠道不存在"),

    ;

    @JsonValue // 存数据库用
    @EnumValue // 返回 JSON 用
    private final Integer type;
    private final String desc;

}
