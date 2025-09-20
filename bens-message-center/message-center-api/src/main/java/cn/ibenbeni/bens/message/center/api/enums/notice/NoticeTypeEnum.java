package cn.ibenbeni.bens.message.center.api.enums.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通知类型枚举
 */
@Getter
@AllArgsConstructor
public enum NoticeTypeEnum {

    NOTICE(1, "通知"),
    ANNOUNCEMENT(2, "公告"),

    ;

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String description;

}
