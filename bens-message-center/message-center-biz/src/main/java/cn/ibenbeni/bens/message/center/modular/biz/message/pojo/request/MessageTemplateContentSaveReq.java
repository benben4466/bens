package cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

@Data
@Schema(description = "管理后台 - 消息模板内容创建/修改入参")
public class MessageTemplateContentSaveReq {

    @Schema(description = "ID", example = "100")
    private Long id;

    @NotNull(message = "模板ID不能为空")
    @Schema(description = "模板ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long templateId;

    @NotNull(message = "渠道类型不能为空")
    @Schema(description = "渠道类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer channelType;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "模板内容")
    private String templateContent;

    @Schema(description = "渠道配置ID集合")
    private Set<Long> channelConfigIds;

}

