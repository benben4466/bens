package cn.ibenbeni.bens.config.modular.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Schema(description = "管理后台 - 参数配置类型创建/修改入参")
public class SysConfigTypeSaveReq {

    @Schema(description = "参数配置类型ID", example = "10")
    private Long configTypeId;

    @NotEmpty(message = "参数配置类型名称不能为空")
    @Size(max = 50, message = "参数配置类型名称不能超过50个字符")
    @Schema(description = "配置类型名称", example = "短信配置", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configTypeName;

    @NotEmpty(message = "参数配置类型编码不能为空")
    @Size(max = 50, message = "参数配置类型编码不能超过50个字符")
    @Schema(description = "配置类型编码", example = "benben.sms", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configTypeCode;

    @NotNull(message = "参数配置类型不能为空")
    @Schema(description = "配置类型", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer configType;

    @NotNull(message = "参数配置类型是否可见不能为空")
    @Schema(description = "配置类型是否可见", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean visibleFlag;

    @Schema(description = "配置类型显示排序", example = "1.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal configTypeSort;

    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

}
