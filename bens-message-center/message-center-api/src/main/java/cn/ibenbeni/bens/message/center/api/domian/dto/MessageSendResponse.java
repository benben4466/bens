package cn.ibenbeni.bens.message.center.api.domian.dto;

import cn.ibenbeni.bens.rule.constants.RuleConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息发送响应
 */
@Data
public class MessageSendResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否成功投递
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * 业务ID
     * <p>回执ID</p>
     */
    private String bizId;

    /**
     * 创建成功响应
     */
    public static MessageSendResponse success(String bizId) {
        MessageSendResponse response = new MessageSendResponse();
        response.setCode(RuleConstants.SUCCESS_CODE);
        response.setMessage("消息投递成功");
        response.setBizId(bizId);
        return response;
    }

    /**
     * 创建失败响应
     */
    public static MessageSendResponse fail(String message) {
        MessageSendResponse response = new MessageSendResponse();
        response.setCode(RuleConstants.SUCCESS_CODE);
        response.setMessage(message);
        return response;
    }

}
