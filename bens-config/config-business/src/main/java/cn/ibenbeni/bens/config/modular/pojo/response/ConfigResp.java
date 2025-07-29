package cn.ibenbeni.bens.config.modular.pojo.response;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "管理后台 - 参数配置信息响应")
@ExcelIgnoreUnannotated
public class ConfigResp {

    @ExcelProperty("参数ID")
    @Schema(description = "参数ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long configId;

    @ExcelProperty("参数配置类型编码")
    @NotEmpty(message = "参数配置类型编码不能为空")
    @Size(max = 50, message = "参数配置类型编码不能超过50个字符")
    @Schema(description = "参数配置类型编码", example = "benben.sms", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configTypeCode;

    @ExcelProperty("参数名称")
    @NotEmpty(message = "参数名称不能为空")
    @Size(max = 50, message = "参数名称不能超过50个字符")
    @Schema(description = "参数名称", example = "验证码开关", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configName;

    @ExcelProperty("参数编码")
    @NotEmpty(message = "参数编码不能为空")
    @Size(max = 50, message = "参数编码不能超过50个字符")
    @Schema(description = "参数编码", example = "sys_captcha_open", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configCode;

    @ExcelProperty("参数值")
    @Schema(description = "参数值", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "参数值不能为空")
    @Size(max = 500, message = "参数值长度不能超过500个字符")
    private String configValue;

    @ExcelProperty("参数类型")
    @NotNull(message = "参数类型不能为空")
    @Schema(description = "参数类型", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer configType;

    @ExcelProperty("是否可见")
    @NotNull(message = "参数是否可见不能为空")
    @Schema(description = "参数是否可见", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean visibleFlag;

    @ExcelProperty("显示排序")
    @Schema(description = "参数显示排序", example = "1.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal configSort;

    @ExcelProperty("备注")
    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

    @ExcelProperty("创建时间")
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
