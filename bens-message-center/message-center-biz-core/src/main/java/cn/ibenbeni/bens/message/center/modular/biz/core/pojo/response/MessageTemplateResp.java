package cn.ibenbeni.bens.message.center.modular.biz.core.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "管理后台 - 消息模板响应")
public class MessageTemplateResp {

    @Schema(description = "模板ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long templateId;

    @Schema(description = "模板编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateCode;

    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateName;

    @Schema(description = "模板状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer templateStatus;

    @Schema(description = "业务类型")
    private String bizType;

    @Schema(description = "支持渠道列表")
    private List<Integer> supportChannels;

    @Schema(description = "审核状态")
    private Integer auditStatus;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核人")
    private Long auditUserId;

    @Schema(description = "审核意见")
    private String auditComment;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;
}

