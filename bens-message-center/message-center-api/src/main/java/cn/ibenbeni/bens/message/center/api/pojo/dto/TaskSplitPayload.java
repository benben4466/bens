package cn.ibenbeni.bens.message.center.api.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 任务拆分 MQ Payload
 */
@Data
public class TaskSplitPayload implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板详情（避免拆分层重复查库）
     */
    private MessageTemplateDTO template;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务ID
     */
    private String bizId;

    /**
     * 接收者类型
     */
    private Integer recipientType;

    /**
     * 接收者信息
     */
    private Map<String, Object> recipient;

    /**
     * 指定渠道列表
     */
    private List<Integer> channels;

    /**
     * 模板参数
     */
    private Map<String, Object> templateParams;

    /**
     * 租户ID
     */
    private Long tenantId;

}
