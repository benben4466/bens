package cn.ibenbeni.bens.sys.api.pojo.role.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * 创建角色入参
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleSaveReqDTO {

    private Long roleId;

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 30, message = "角色名称长度不能超过30个字符")
    private String roleName;

    @NotBlank(message = "角色编码不能为空")
    @Size(max = 100, message = "角色编码长度不能超过100个字符")
    private String roleCode;

    @NotNull(message = "显示顺序不能为空")
    private BigDecimal roleSort;

    /**
     * 枚举值：{@link cn.ibenbeni.bens.rule.enums.StatusEnum}
     */
    @NotNull(message = "角色状态不能为空")
    private Integer statusFlag;

    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;

}
