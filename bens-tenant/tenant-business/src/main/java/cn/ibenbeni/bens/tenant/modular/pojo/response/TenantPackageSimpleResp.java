package cn.ibenbeni.bens.tenant.modular.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台 - 租户套餐精简响应")
public class TenantPackageSimpleResp {

    @Schema(description = "租户套餐ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long packageId;

    @Schema(description = "租户套餐名称", example = "入门套餐", requiredMode = Schema.RequiredMode.REQUIRED)
    private String packageName;

}
