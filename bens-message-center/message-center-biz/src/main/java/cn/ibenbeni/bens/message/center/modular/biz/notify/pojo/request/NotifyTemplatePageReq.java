package cn.ibenbeni.bens.message.center.modular.biz.notify.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.ibenbeni.bens.rule.util.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 站内信模版分页入参")
public class NotifyTemplatePageReq extends PageParam {

    @Schema(description = "站内信模板名称", example = "发送提问", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "站内信模板编码", example = "SEND_QA", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "站内信模板状态(StatusEnum)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
