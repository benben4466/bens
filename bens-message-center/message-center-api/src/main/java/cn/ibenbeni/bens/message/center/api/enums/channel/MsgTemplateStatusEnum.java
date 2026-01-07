package cn.ibenbeni.bens.message.center.api.enums.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息模板-状态
 */
@Getter
@AllArgsConstructor
public enum MsgTemplateStatusEnum {

    DRAFT(10, "草稿"),
    PENDING(20, "待审核"),
    ONLINE(30, "已上线"),
    OFFLINE(40, "已下线"),

    ;

    private final Integer status;
    private final String desc;

}
