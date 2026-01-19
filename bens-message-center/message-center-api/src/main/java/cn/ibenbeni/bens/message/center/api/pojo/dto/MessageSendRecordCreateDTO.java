package cn.ibenbeni.bens.message.center.api.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 创建消息发送记录请求DTO
 */
@Data
public class MessageSendRecordCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关联模板ID
     */
    private Long templateId;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务关联ID
     */
    private String bizId;

    /**
     * 发送时的标题
     */
    private String msgTitle;

    /**
     * 模板参数变量
     */
    private Map<String, Object> msgVariables;

    /**
     * 渠道类型
     */
    private Integer channelType;

    /**
     * 接收者信息
     */
    private Map<String, Object> recipient;

    /**
     * 接收人类型
     */
    private Integer recipientType;

    /**
     * 租户ID
     */
    private Long tenantId;

}
