package cn.ibenbeni.bens.tenant.modular.pojo.response;

import cn.ibenbeni.bens.rule.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Schema(description = "管理后台 - 租户套餐响应")
public class TenantPackageResp {

    @Schema(description = "租户套餐ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long packageId;

    @Schema(description = "租户套餐名称", example = "入门套餐", requiredMode = Schema.RequiredMode.REQUIRED)
    private String packageName;

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

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
