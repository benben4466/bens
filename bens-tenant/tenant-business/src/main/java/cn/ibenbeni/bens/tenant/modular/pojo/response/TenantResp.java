package cn.ibenbeni.bens.tenant.modular.pojo.response;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExcelIgnoreUnannotated
@Schema(description = "管理后台 - 租户 Response VO")
public class TenantResp {

    @Schema(description = "租户ID", example = "10")
    private Long tenantId;

    @Schema(description = "租户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "笨笨科技")
    private String tenantName;

    @Schema(description = "租户联系人名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "笨笨")
    private String contactName;

    @Schema(description = "租户联系人手机号码", requiredMode = Schema.RequiredMode.REQUIRED, example = "15291744123")
    private String contactMobile;

    @Schema(description = "租户状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer statusFlag;

    @Schema(description = "租户绑定的域名", example = "www.ibenbeni.com")
    private String tenantWebsite;

    @Schema(description = "租户套餐ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long tenantPackageId;

    @Schema(description = "租户过期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date expireTime;

    @Schema(description = "授权的账号数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "8")
    private Long accountCount;

    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

    @ExcelProperty("创建时间")
    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
