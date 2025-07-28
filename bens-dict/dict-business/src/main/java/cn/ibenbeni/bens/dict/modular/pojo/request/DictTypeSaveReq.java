package cn.ibenbeni.bens.dict.modular.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Schema(description = "管理后台 - 字典类型创建/修改入参")
public class DictTypeSaveReq {

    @Schema(description = "字典类型ID", example = "10")
    private Long dictTypeId;

    @NotBlank(message = "字典类型名称不能为空")
    @Size(max = 100, message = "字典类型名称长度不能超过100个字符")
    @Schema(description = "字典类型名称", example = "笨笨字典类型名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictTypeName;

    @NotBlank(message = "字典类型编码不能为空")
    @Size(max = 100, message = "字典类型编码长度不能超过100个字符")
    @Schema(description = "字典编码名称", example = "笨笨字典编码名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictTypeCode;

    /**
     * 状态
     * <p>枚举: {@link cn.ibenbeni.bens.rule.enums.StatusEnum}</p>
     */
    @NotNull(message = "字典状态不能为空")
    @Schema(description = "字典状态", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    @Schema(description = "字典类型显示排序", example = "1.0")
    private BigDecimal dictTypeSort;

    /**
     * 备注
     */
    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

}
