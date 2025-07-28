package cn.ibenbeni.bens.dict.modular.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "管理后台 - 字典类型信息响应")
public class DictTypeResp {

    @Schema(description = "字典类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long dictTypeId;

    @Schema(description = "字典类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "性别")
    private String dictTypeName;

    @Schema(description = "字典类型编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "sys_sex")
    private String dictTypeCode;

    @Schema(description = "字典类型状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer statusFlag;

    @Schema(description = "字典类型显示排序", requiredMode = Schema.RequiredMode.REQUIRED, example = "1.0")
    private BigDecimal dictTypeSort;

    @Schema(description = "字典类型备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
