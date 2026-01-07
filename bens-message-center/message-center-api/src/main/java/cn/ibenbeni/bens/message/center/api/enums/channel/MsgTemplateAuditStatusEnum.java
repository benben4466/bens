package cn.ibenbeni.bens.message.center.api.enums.channel;

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

    private final Integer status;
    private final String desc;

}
