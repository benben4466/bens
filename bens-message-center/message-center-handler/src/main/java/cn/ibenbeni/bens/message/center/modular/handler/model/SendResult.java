package cn.ibenbeni.bens.message.center.modular.handler.model;

import lombok.Data;

/**
 * 渠道发送结果
 */
@Data
public class SendResult {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 渠道方消息ID
     */
    private String channelMsgId;

    /**
     * 响应数据
     */
    private String responseData;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 创建成功结果
     */
    public static SendResult success(String channelMsgId, String responseData) {
        SendResult result = new SendResult();
        result.setSuccess(true);
        result.setChannelMsgId(channelMsgId);
        result.setResponseData(responseData);
        return result;
    }

    /**
     * 创建失败结果
     */
    public static SendResult fail(String errorCode, String errorMessage) {
        SendResult result = new SendResult();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage);
        return result;
    }

}
