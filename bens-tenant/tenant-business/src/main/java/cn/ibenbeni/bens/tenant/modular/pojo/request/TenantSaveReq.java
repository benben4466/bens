package cn.ibenbeni.bens.tenant.modular.pojo.request;

import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.rule.util.DateUtils;
import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Schema(description = "管理后台 - 租户创建/修改入参")
public class TenantSaveReq {

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

    @JsonFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = "GMT+8")
    @Schema(description = "租户过期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date expireTime;

    @Schema(description = "授权的账号数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "8")
    private Long accountCount;

    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

    // region 创建时使用

    /**
     * 用户账号
     */
    @Pattern(regexp = "^[a-zA-Z0-9]{4,30}$", message = "用户账号由 数字、字母 组成")
    @Schema(description = "用户账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "benben")
    private String userAccount;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    private String userPassword;

    @JSONField(serialize = false)
    @AssertTrue(message = "用户账号,密码不能为空")
    public boolean isUserInfoValid() {
        // 修改时，不需要传入用户账号和密码
        return tenantId != null || ObjectUtil.isAllNotEmpty(userAccount, userPassword);
    }

    // endregion

}
