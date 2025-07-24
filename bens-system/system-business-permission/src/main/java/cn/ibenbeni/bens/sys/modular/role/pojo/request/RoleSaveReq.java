package cn.ibenbeni.bens.sys.modular.role.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Schema(description = "管理后台 - 角色创建/更新入参")
public class RoleSaveReq {

    @Schema(description = "角色ID", example = "10")
    private Long roleId;

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 30, message = "角色名称长度不能超过30个字符")
    @Schema(description = "角色名称", example = "笨笨角色", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleName;

    @NotBlank(message = "角色编码不能为空")
    @Size(max = 100, message = "角色编码长度不能超过100个字符")
    @Schema(description = "角色编码", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleCode;

    @NotNull(message = "显示顺序不能为空")
    @Schema(description = "角色显示顺序", example = "1.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal roleSort;

    /**
     * 枚举值：{@link cn.ibenbeni.bens.rule.enums.StatusEnum}
     */
    @NotNull(message = "角色状态不能为空")
    @Schema(description = "角色状态", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Schema(description = "备注", example = "笨笨角色备注")
    private String remark;

}
