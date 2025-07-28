package cn.ibenbeni.bens.dict.modular.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台 - 字典类型简略信息响应")
public class DictTypeSimpleResp {

    @Schema(description = "字典类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long dictTypeId;

    @Schema(description = "字典类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "性别")
    private String dictTypeName;

    @Schema(description = "字典类型编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "sys_sex")
    private String dictTypeCode;

}
