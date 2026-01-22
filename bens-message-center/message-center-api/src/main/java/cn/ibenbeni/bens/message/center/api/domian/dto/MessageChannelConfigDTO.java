package cn.ibenbeni.bens.message.center.api.domian.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 消息渠道配置传输对象
 */
@Data
public class MessageChannelConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 渠道类型
     */
    private Integer channelType;

    /**
     * 账户配置
     */
    private Map<String, Object> accountConfig;

    /**
     * 状态
     */
    private Integer statusFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户ID
     */
    private Long tenantId;

}
