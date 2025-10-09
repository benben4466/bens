package cn.ibenbeni.bens.iot.modular.base.pojo.request.product;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.ibenbeni.bens.rule.util.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - IoT 产品分类分页 Request VO")
public class IotProductCategoryPageReq extends PageParam {

    @Schema(description = "产品分类名称", example = "笨笨烟感器")
    private String categoryName;

    @Schema(description = "产品分类编码", example = "benben_test")
    private String categoryCode;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
