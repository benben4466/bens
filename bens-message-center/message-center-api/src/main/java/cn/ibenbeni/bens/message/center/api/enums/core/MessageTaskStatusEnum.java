package cn.ibenbeni.bens.message.center.api.enums.core;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息发送任务状态
 */
@Getter
@AllArgsConstructor
public enum MessageTaskStatusEnum {

    WAITING_SPLIT(0, "待拆分"),
    PROCESSING(10, "处理中"),
    COMPLETED(20, "已完成"),
    PARTIAL_FAIL(30, "部分失败"),
    ;

    @JsonValue
    @EnumValue
    private final Integer status;
    private final String desc;
}
