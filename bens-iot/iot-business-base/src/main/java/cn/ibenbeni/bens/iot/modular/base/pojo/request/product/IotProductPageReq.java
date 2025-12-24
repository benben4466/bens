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
public class IotProductPageReq extends PageParam {

    @Schema(description = "产品名称", example = "笨笨1号产品")
    private String productName;

    @Schema(description = "产品Key", example = "pt7hkhtmZSD8kz2e")
    private String productKey;

    @Schema(description = "产品分类名称", example = "笨笨产品")
    private String categoryName;

    @Schema(description = "产品状态", example = "0")
    private Integer statusFlag;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
