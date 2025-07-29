package cn.ibenbeni.bens.config.modular.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Schema(description = "管理后台 - 参数保存/修改入参")
public class SysConfigSaveReq {

    @Schema(description = "参数ID", example = "10")
    private Long configId;

    @NotEmpty(message = "参数配置类型编码不能为空")
    @Size(max = 50, message = "参数配置类型编码不能超过50个字符")
    @Schema(description = "参数配置类型编码", example = "benben.sms", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configTypeCode;

    @NotEmpty(message = "参数名称不能为空")
    @Size(max = 50, message = "参数名称不能超过50个字符")
    @Schema(description = "参数名称", example = "验证码开关", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configName;

    @NotEmpty(message = "参数编码不能为空")
    @Size(max = 50, message = "参数编码不能超过50个字符")
    @Schema(description = "参数编码", example = "sys_captcha_open", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configCode;

    @Schema(description = "参数值", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "参数值不能为空")
    @Size(max = 500, message = "参数值长度不能超过500个字符")
    private String configValue;

    @NotNull(message = "参数类型不能为空")
    @Schema(description = "参数类型", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer configType;

    @NotNull(message = "参数是否可见不能为空")
    @Schema(description = "参数是否可见", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean visibleFlag;

    @Schema(description = "参数显示排序", example = "1.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal configSort;

    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

}
