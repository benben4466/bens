package cn.ibenbeni.bens.message.center.modular.execute.model;

import cn.ibenbeni.bens.common.chain.core.BaseChainContext;
import cn.ibenbeni.bens.message.center.api.domian.core.ParsedChannelContent;
import cn.ibenbeni.bens.message.center.api.domian.recipient.AbstractRecipientInfo;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendFailTypeEnum;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * 消息处理上下文
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageHandleContext extends BaseChainContext {


    // region 不允许修改

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 渠道类型
     */
    private Integer channelType;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 原始模板变量
     */
    private Map<String, Object> msgVariables;

    /**
     * 接收者信息
     */
    private List<AbstractRecipientInfo> recipientInfos;

    /**
     * 消息发送明细
     */
    private Long msgSendDetailId;

    // endregion

    /**
     * 模板内容ID
     */
    private Long templateContentId;

    /**
     * 已解析内容
     */
    private ParsedChannelContent parsedContent;

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
