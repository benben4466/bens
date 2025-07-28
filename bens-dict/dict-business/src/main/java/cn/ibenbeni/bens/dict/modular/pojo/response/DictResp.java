package cn.ibenbeni.bens.dict.modular.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "管理后台 - 字典信息响应")
public class DictResp {

    @Schema(description = "字典ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long dictId;

    @Schema(description = "字典类型编码", example = "sys_sex", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictTypeCode;

    @Schema(description = "字典编码", example = "男", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictCode;

    @Schema(description = "字典值", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictValue;

    @Schema(description = "字典颜色类型", example = "default")
    private String dictColorType;

    @Schema(description = "字典显示排序", example = "1.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal dictSort;

    @Schema(description = "字典状态", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    @Schema(description = "字典备注", example = "笨笨备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
