package cn.ibenbeni.bens.config.modular.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.rule.util.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 参数分页查询入参")
public class SysConfigPageReq extends PageParam {

    @Schema(description = "参数名称,模糊匹配", example = "验证码开关", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configName;

    @Schema(description = "参数编码,模糊匹配", example = "sys_captcha_open", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configCode;

    @Schema(description = "参数类型", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer configType;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}
