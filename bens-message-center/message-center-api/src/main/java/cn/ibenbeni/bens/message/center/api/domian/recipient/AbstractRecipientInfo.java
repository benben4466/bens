package cn.ibenbeni.bens.message.center.api.domian.recipient;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 接收者信息
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "channelType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmailRecipientInfo.class, name = "30")
})
public abstract class AbstractRecipientInfo {

    /**
     * 渠道类型枚举
     */
    private Integer channelType;

    /**
     * 标识值
     * <p>如：手机号、邮箱、用户ID等</p>
     */
    private List<String> identifiers;

    /**
     * 附加属性（可选）
     * <p>如：地区、语言偏好等</p>
     */
    private Map<String, Object> attributes;

}
