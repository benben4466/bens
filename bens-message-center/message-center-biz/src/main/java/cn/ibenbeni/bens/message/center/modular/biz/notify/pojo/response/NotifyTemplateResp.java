package cn.ibenbeni.bens.message.center.modular.biz.notify.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "管理后台 - 站内信模版响应")
public class NotifyTemplateResp {

    @Schema(description = "站内信模板ID", example = "10")
    private Long id;

    @Schema(description = "站内信模板名称", example = "发送提问", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "站内信模板编码", example = "SEND_QA", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "站内信发送人名称", example = "笨笨", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @Schema(description = "站内信模板内容", example = "笨笨科技成立", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "站内信模板类型", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer type;

    @Schema(description = "参数数组", example = "name,code")
    private List<String> params;

    @Schema(description = "站内信模板状态(StatusEnum)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    @Schema(description = "站内信模板备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
