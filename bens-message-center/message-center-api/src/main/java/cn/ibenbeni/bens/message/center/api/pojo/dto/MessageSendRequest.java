package cn.ibenbeni.bens.message.center.api.pojo.dto;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgRecipientTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 消息发送请求
 */
@Data
public class MessageSendRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板编码
     */
    @NotEmpty(message = "模板编码不能为空")
    private String templateCode;

    /**
     * 模板参数
     */
    private Map<String, Object> templateParams;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 接收者类型
     * <p>枚举：{@link MsgRecipientTypeEnum}</p>
     */
    private Integer recipientType;

    /**
     * 接收者信息
     * 可包含: userId, phone, email, deviceToken 等
     */
    private Map<String, Object> recipient;

    /**
     * 指定渠道列表（可选）
     * 为空则使用模板配置的渠道
     */
    private List<Integer> channels;

    /**
     * 是否系统发送
     * <p>若为 true，则不用</p>
     * <p>注意：此参数不能暴露给用户</p>
     */
    private Boolean isSysSend;

}
