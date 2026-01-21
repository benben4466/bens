package cn.ibenbeni.bens.message.center.api.enums.core;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息模板-审核状态
 */
@Getter
@AllArgsConstructor
public enum MsgTemplateAuditStatusEnum {

    PENDING(10, "待审核"),
    APPROVED(20, "审核通过"),
    REJECTED(30, "审核失败"),
    ;

    @JsonValue // 存数据库用
    @EnumValue // 返回 JSON 用
    private final Integer status;
    private final String desc;

}
