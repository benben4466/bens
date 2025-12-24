package cn.ibenbeni.bens.iot.modular.base.pojo.response.product;

import cn.ibenbeni.bens.rule.util.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Schema(description = "管理后台 - IoT 产品分类响应")
public class IotProductCategoryResp {

    @Schema(description = "产品分类ID", example = "10")
    private Long categoryId;

    @Schema(description = "产品分类名称", example = "笨笨烟感器")
    private String categoryName;

    @Schema(description = "产品分类编码", example = "benben_test")
    private String categoryCode;

    @Schema(description = "产品分类排序", example = "1.0")
    private String categorySort;

    @Schema(description = "产品分类状态", example = "1")
    private Integer statusFlag;

    @Schema(description = "是否系统内置", example = "1")
    private Integer isSys;

    @Schema(description = "备注")
    private String remark;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
