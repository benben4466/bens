package cn.ibenbeni.bens.message.center.api.enums.core;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息中心-接收人类型
 */
@Getter
@AllArgsConstructor
public enum MsgRecipientTypeEnum {

    MOBILE_PHONE(10, "移动手机号"),
    EMAIL_ACCOUNT(20, "邮件账号"),
    USER_ID(30, "用户ID"),
    ;

    @JsonValue // 存数据库用
    @EnumValue // 返回 JSON 用
    private final Integer type;
    private final String desc;

}
