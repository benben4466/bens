package cn.ibenbeni.bens.message.center.api.domian.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 消息模板内容传输对象
 */
@Data
public class MessageTemplateContentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 内容ID
     */
    private Long id;

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
    private String title;

    /**
     * 模板内容
     */
    private String templateContent;

    /**
     * 参数配置
     */
    private Map<String, Object> paramsConfig;

    /**
     * 渠道特定配置
     */
    private Map<String, Object> channelConfig;

    /**
     * 租户ID
     */
    private Long tenantId;

}
