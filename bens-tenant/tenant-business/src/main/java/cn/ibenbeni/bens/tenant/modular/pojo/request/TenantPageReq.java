package cn.ibenbeni.bens.tenant.modular.pojo.request;

import cn.ibenbeni.bens.db.api.pojo.page.PageParam;
import cn.ibenbeni.bens.rule.util.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 租户分页入参")
public class TenantPageReq extends PageParam {

    @Schema(description = "租户名称", example = "笨笨科技")
    private String tenantName;

    @Schema(description = "租户联系人名称", example = "笨笨")
    private String contactName;

    @Schema(description = "租户联系人手机号码", example = "15291744123")
    private String contactMobile;

    @Schema(description = "租户状态", example = "1")
    private Integer statusFlag;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}
