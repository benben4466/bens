package cn.ibenbeni.bens.message.center.modular.biz.notify.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "管理后台 - 站内信模版创建/修改入参")
public class NotifyTemplateSaveReq {

    @Schema(description = "站内信模板ID", example = "10")
    private Long id;

    @NotEmpty(message = "模版名称不能为空")
    @Schema(description = "站内信模板名称", example = "发送提问", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotEmpty(message = "模版编码不能为空")
    @Schema(description = "站内信模板编码", example = "SEND_QA", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotEmpty(message = "发送人名称不能为空")
    @Schema(description = "站内信发送人名称", example = "笨笨", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @NotEmpty(message = "模板内容不能为空")
    @Schema(description = "站内信模板内容", example = "笨笨科技成立", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @NotNull(message = "模版类型不能为空")
    @Schema(description = "站内信模板类型", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer type;

    @NotNull(message = "模板状态不能为空")
    @Schema(description = "站内信模板状态(StatusEnum)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    @Schema(description = "站内信模板备注")
    private String remark;

}
