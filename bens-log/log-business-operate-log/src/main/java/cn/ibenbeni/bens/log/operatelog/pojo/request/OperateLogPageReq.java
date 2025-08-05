package cn.ibenbeni.bens.log.operatelog.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.ibenbeni.bens.rule.util.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 操作日志分页列表入参")
public class OperateLogPageReq extends PageParam {

    @Schema(description = "用户ID", example = "benben")
    private Long userId;

    @Schema(description = "操作模块业务ID", example = "123")
    private Long bizId;

    @Schema(description = "操作模块编号", example = "商品")
    private String moduleNo;

    @Schema(description = "操作子模块编号", example = "删除商品")
    private String subModuleNo;

    @Schema(description = "操作内容", example = "删除ID为10的商品")
    private String opAction;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "查询时间")
    private LocalDateTime[] createTime;

}
