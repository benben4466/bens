package cn.ibenbeni.bens.message.center.api.domian.dto;

import cn.ibenbeni.bens.message.center.api.enums.core.MessageDetailStatusEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgPushChannelTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 消息发送明细 DTO
 */
@Data
public class MessageSendDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 父任务ID
     */
    private Long taskId;

    /**
     * 接收者标识(手机号/邮箱/OpenID)
     */
    private String targetUser;

    /**
     * 渠道类型
     */
    private MsgPushChannelTypeEnum channelType;

    /**
     * 模板参数变量
     */
    private Map<String, Object> msgVariables;

    /**
     * 发送状态
     */
    private MessageDetailStatusEnum sendStatus;

    /**
     * 三方渠道返回唯一标识
     */
    private String outSerialNumber;

    /**
     * 三方渠道返回错误信息
     */
    private String outResp;

    /**
     * 实际发送完成时间
     */
    private Date finishTime;

    /**
     * 已重试次数
     */
    private Integer retryCount;

    /**
     * 最大重试次数
     */
    private Integer maxRetry;

    /**
     * 下次重试时间
     */
    private Date nextRetryTime;

    /**
     * 租户编号
     */
    private Long tenantId;

}
