package cn.ibenbeni.bens.dict.modular.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 字典类型分页列表入参")
public class DictPageReq extends PageParam {

    @Size(max = 100, message = "字典类型编码长度不能超过100个字符")
    @Schema(description = "字典类型编码", example = "sys_sex")
    private String dictTypeCode;

    @Size(max = 100, message = "字典编码长度不能超过100个字符")
    @Schema(description = "字典编码", example = "男")
    private String dictCode;

    @Schema(description = "字典状态", example = "1")
    private Integer statusFlag;

}
