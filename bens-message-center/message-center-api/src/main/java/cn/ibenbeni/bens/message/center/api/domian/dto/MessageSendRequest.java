package cn.ibenbeni.bens.message.center.api.domian.dto;

import cn.ibenbeni.bens.message.center.api.domian.recipient.AbstractRecipientInfo;
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
     * 接收者信息
     */
    private List<AbstractRecipientInfo> recipientInfos;

}
