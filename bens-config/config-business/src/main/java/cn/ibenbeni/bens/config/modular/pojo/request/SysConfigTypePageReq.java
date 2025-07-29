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
@Schema(description = "管理后台 - 参数类型分页查询入参")
public class SysConfigTypePageReq extends PageParam {

    @Schema(description = "参数类型名称,模糊匹配", example = "短信配置")
    private String configTypeName;

    @Schema(description = "参数类型编码,模糊匹配", example = "benben.sms")
    private String configTypeCode;

    @Schema(description = "参数类型", example = "1")
    private Integer configType;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}
