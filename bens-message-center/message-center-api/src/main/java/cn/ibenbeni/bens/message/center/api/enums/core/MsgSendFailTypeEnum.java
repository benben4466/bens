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
    CHANNEL_NOT_SUPPORT(1, "不支持的渠道类型"),
    CHANNEL_NOT_AVAILABLE(2, "渠道不可用"),
    CHANNEL_SEND_FAIL(3, "渠道发送失败"),
    SENSITIVE_WORD_DETECTED(4, "包含敏感词"),
    SYSTEM_ERROR(5, "系统错误"),
    TEMPLATE_NOT_FOUND(6, "未找到模板内容"),
    TEMPLATE_PARSE_FAIL(7, "模板解析失败"),

    ;

    @JsonValue // 存数据库用
    @EnumValue // 返回 JSON 用
    private final Integer type;
    private final String desc;

}
