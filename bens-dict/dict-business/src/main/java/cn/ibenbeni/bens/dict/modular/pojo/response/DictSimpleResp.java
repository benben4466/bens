package cn.ibenbeni.bens.dict.modular.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台 - 数据字典简略响应")
public class DictSimpleResp {

    @Schema(description = "字典类型编码", example = "sys_sex", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictTypeCode;

    @Schema(description = "字典值", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictValue;

    @Schema(description = "字典编码", example = "男", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictCode;

    @Schema(description = "颜色类型", example = "default")
    private String dictColorType;

}
