package cn.ibenbeni.bens.message.center.api.domian.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 消息发送响应
 */
@Data
public class MessageSendResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否成功投递
     */
    private Boolean success;

    /**
     * 消息
     */
    private String message;

    /**
     * 发送记录ID列表（每个渠道一条记录）
     */
    private List<Long> recordIds;

    /**
     * 创建成功响应
     */
    public static MessageSendResponse success(List<Long> recordIds) {
        MessageSendResponse response = new MessageSendResponse();
        response.setSuccess(true);
        response.setMessage("消息投递成功");
        response.setRecordIds(recordIds);
        return response;
    }

    /**
     * 创建失败响应
     */
    public static MessageSendResponse fail(String message) {
        MessageSendResponse response = new MessageSendResponse();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }

}
