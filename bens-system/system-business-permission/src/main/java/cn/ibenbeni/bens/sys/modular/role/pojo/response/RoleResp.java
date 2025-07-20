package cn.ibenbeni.bens.sys.modular.role.pojo.response;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@ExcelIgnoreUnannotated
@Data
@Schema(description = "管理后台 - 角色信息响应")
public class RoleResp {

    @ExcelProperty("角色ID")
    @Schema(description = "角色ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long roleId;

    @ExcelProperty("角色名称")
    @Schema(description = "角色名称", example = "笨笨角色", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleName;

    @ExcelProperty("角色编码")
    @Schema(description = "角色编码", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleCode;

    @ExcelProperty("角色显示顺序")
    @Schema(description = "角色显示顺序", example = "1.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal roleSort;

    /**
     * <p>枚举值：{@link cn.ibenbeni.bens.rule.enums.permission.DataScopeTypeEnum}</p>
     */
    @ExcelProperty("角色数据权限类型")
    @Schema(description = "角色数据权限类型", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer dataScopeType;

    @Schema(description = "数据权限部门ID集合", example = "[1,10]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<Long> dataScopeDeptIds;

    @ExcelProperty("角色状态")
    @Schema(description = "角色状态", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    /**
     * <p>枚举值：{@link cn.ibenbeni.bens.sys.api.enums.role.RoleTypeEnum}</p>
     */
    @Schema(description = "角色类型", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer roleType;

    @Schema(description = "备注", example = "笨笨角色备注")
    private String remark;

}
