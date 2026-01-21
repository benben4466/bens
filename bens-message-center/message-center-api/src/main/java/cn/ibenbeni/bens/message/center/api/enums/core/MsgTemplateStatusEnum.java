package cn.ibenbeni.bens.message.center.api.enums.core;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
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

    @JsonValue // 存数据库用
    @EnumValue // 返回 JSON 用
    private final Integer status;
    private final String desc;

}
