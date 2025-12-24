package cn.ibenbeni.bens.iot.modular.base.pojo.request.rule;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.util.DateUtils;
import cn.ibenbeni.bens.validator.api.validators.enums.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - IoT场景联动分页入参")
public class IotSceneRulePageReq extends PageParam {

    @Schema(description = "场景名称", example = "笨笨1号测试场景1")
    private String name;

    @Schema(description = "场景描述", example = "笨笨场景")
    private String description;

    @Schema(description = "场景状态", example = "1")
    @InEnum(StatusEnum.class)
    private Integer statusFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
