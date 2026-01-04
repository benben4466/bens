package cn.ibenbeni.bens.message.center.modular.biz.core.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Schema(description = "管理后台 - 消息模板内容响应")
public class MessageTemplateContentResp {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "模板ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long templateId;

    @Schema(description = "渠道类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer channelType;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "模板内容")
    private String templateContent;

    @Schema(description = "参数配置")
    private Map<String, Object> paramsConfig;

    @Schema(description = "渠道特定配置")
    private Map<String, Object> channelConfig;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;
}

