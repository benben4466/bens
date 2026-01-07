package cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request;

import cn.ibenbeni.bens.rule.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "管理后台 - 消息模板创建/修改入参")
public class MessageTemplateSaveReq {

    @Schema(description = "模板ID", example = "100")
    private Long templateId;

    @NotBlank(message = "模板编码不能为空")
    @Size(max = 255, message = "模板编码不能超过255个字符")
    @Schema(description = "模板编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateCode;

    @NotBlank(message = "模板名称不能为空")
    @Size(max = 255, message = "模板名称不能超过255个字符")
    @Schema(description = "模板名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateName;

    @Schema(description = "模板状态", example = "10")
    private Integer templateStatus;

    @Schema(description = "业务类型")
    private String bizType;

    @Schema(description = "支持渠道列表，channelType 枚举值集合")
    private List<Integer> supportChannels;

    @Schema(description = "审核状态", example = "10")
    private Integer auditStatus;

    @JsonFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核人")
    private Long auditUserId;

    @Schema(description = "审核意见")
    private String auditComment;

    @Schema(description = "模板内容列表")
    private List<MessageTemplateContentSaveReq> contentList;
}

