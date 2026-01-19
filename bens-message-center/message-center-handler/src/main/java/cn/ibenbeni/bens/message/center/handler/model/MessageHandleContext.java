package cn.ibenbeni.bens.message.center.handler.model;

import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendFailTypeEnum;
import cn.ibenbeni.bens.message.center.common.chain.BaseChainContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 消息处理上下文
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageHandleContext extends BaseChainContext {

    /**
     * 发送记录ID
     */
    private Long recordId;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 渠道类型
     */
    private Integer channelType;

    /**
     * 消息标题
     */
    private String messageTitle;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 接收者信息
     */
    private Map<String, Object> recipient;

    /**
     * 接收者类型
     */
    private Integer recipientType;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 渠道配置
     */
    private Map<String, Object> channelConfig;

    /**
     * 原始模板变量
     */
    private Map<String, Object> msgVariables;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 是否成功
     */
    private boolean success = false;

    /**
     * 失败类型
     */
    private MsgSendFailTypeEnum failType;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 响应数据
     */
    private String responseData;

}
