package cn.ibenbeni.bens.config.modular.pojo.response;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "管理后台 - 参数类型信息响应")
@ExcelIgnoreUnannotated
public class ConfigTypeResp {

    @ExcelProperty("参数类型ID")
    @Schema(description = "参数配置类型ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long configTypeId;

    @ExcelProperty("参数类型名称")
    @NotEmpty(message = "参数配置类型名称不能为空")
    @Size(max = 50, message = "参数配置类型名称不能超过50个字符")
    @Schema(description = "配置类型名称", example = "短信配置", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configTypeName;

    @ExcelProperty("参数类型编码")
    @NotEmpty(message = "参数配置类型编码不能为空")
    @Size(max = 50, message = "参数配置类型编码不能超过50个字符")
    @Schema(description = "配置类型编码", example = "benben.sms", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configTypeCode;

    @ExcelProperty("参数类型")
    @NotNull(message = "参数配置类型不能为空")
    @Schema(description = "配置类型", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer configType;

    @ExcelProperty("是否可见")
    @NotNull(message = "参数配置类型是否可见不能为空")
    @Schema(description = "配置类型是否可见", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean visibleFlag;

    @ExcelProperty("显示排序")
    @Schema(description = "配置类型显示排序", example = "1.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal configTypeSort;

    @ExcelProperty("备注")
    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

    @ExcelProperty("创建时间")
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
