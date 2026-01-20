package cn.ibenbeni.bens.message.center.api.enums.core;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息发送明细状态
 */
@Getter
@AllArgsConstructor
public enum MessageDetailStatusEnum {

    PENDING(0, "待处理"),
    SUCCESS(10, "成功"),
    FAIL(20, "失败"),
    ;

    @JsonValue
    @EnumValue
    private final Integer status;
    private final String desc;
}
