package cn.ibenbeni.bens.dict.modular.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Schema(description = "管理后台 - 字典创建/修改入参")
public class DictSaveReq {

    @Schema(description = "字典ID", example = "10")
    private Long dictId;

    @NotBlank(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型长度不能超过100个字符")
    @Schema(description = "字典类型", example = "sys_sex", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictTypeCode;

    @NotBlank(message = "字典编码不能为空")
    @Size(max = 100, message = "字典编码长度不能超过100个字符")
    @Schema(description = "字典编码", example = "男", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictCode;

    @NotBlank(message = "字典值不能为空")
    @Size(max = 100, message = "字典值长度不能超过100个字符")
    @Schema(description = "字典值", example = "ibenbeni", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictValue;

    @Schema(description = "颜色类型", example = "default")
    private String dictColorType;

    @NotNull(message = "字典显示顺序不能为空")
    @Schema(description = "字典显示顺序", example = "1.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal dictSort;

    /**
     * <p>枚举值: {@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @NotNull(message = "字典状态不能为空")
    @Schema(description = "字典状态", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    @Schema(description = "字典备注", example = "笨笨备注")
    private String remark;

}
