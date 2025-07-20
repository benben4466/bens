package cn.ibenbeni.bens.sys.modular.role.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "管理后台 - 角色创建/更新入参")
public class RoleSaveReq {

    @Schema(description = "角色ID", example = "10")
    private Long roleId;

    @Schema(description = "角色名称", example = "笨笨角色", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleName;

    @Schema(description = "角色编码", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleCode;

    @Schema(description = "角色显示顺序", example = "1.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal roleSort;

    /**
     * 枚举值：{@link cn.ibenbeni.bens.rule.enums.StatusEnum}
     */
    @Schema(description = "角色状态", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    @Schema(description = "备注", example = "笨笨角色备注")
    private String remark;

}
