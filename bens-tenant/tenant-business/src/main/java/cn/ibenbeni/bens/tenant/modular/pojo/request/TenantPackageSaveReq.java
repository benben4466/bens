package cn.ibenbeni.bens.tenant.modular.pojo.request;

import cn.ibenbeni.bens.rule.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Schema(description = "管理后台 - 租户套餐创建/修改入参")
public class TenantPackageSaveReq {

    @Schema(description = "租户套餐ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long packageId;

    @NotBlank(message = "租户套餐名称不能为空")
    @Schema(description = "租户套餐名称", example = "入门套餐", requiredMode = Schema.RequiredMode.REQUIRED)
    private String packageName;

    @NotNull
    @Schema(description = "关联的菜单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<Long> packageMenuIds;

    /**
     * 状态
     * <p>枚举：{@link StatusEnum}</p>
     */
    @Schema(description = "租户状态", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusFlag;

    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

}
