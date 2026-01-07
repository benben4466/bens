package cn.ibenbeni.bens.message.center.api.enums.core;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息中心-消息发送状态
 */
@Getter
@AllArgsConstructor
public enum MsgSendStatusEnum {

    PENDING(10, "待发送"),
    SENDING(20, "发送中"),
    SUCCESS(30, "成功"),
    FAILED(40, "失败"),
    ;

    @JsonValue // 存数据库用
    @EnumValue // 返回 JSON 用
    private final Integer status;
    private final String desc;

}
