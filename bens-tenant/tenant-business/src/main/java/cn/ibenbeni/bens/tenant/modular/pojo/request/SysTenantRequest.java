package cn.ibenbeni.bens.tenant.modular.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 租户请求参数
 *
 * @author: benben
 * @time: 2025/7/1 下午1:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysTenantRequest extends BaseRequest {

    /**
     * 租户ID
     */
    @NotNull(message = "租户编号不能为空")
    private Long tenantId;

    /**
     * 租户名称
     */
    @NotBlank(message = "租户名称不能为空")
    private String tenantName;

    /**
     * 租户联系人名称
     */
    @NotBlank(message = "联系人名称不能为空")
    private String contactName;

    /**
     * 租户联系人手机号码
     */
    private String contactMobile;

    /**
     * 租户状态
     * <p>1=正常，2=禁用</p>
     */
    @NotNull(message = "租户状态不能为空")
    private Integer status_flag;

    /**
     * 租户绑定的域名
     */
    private String tenantWebsite;

    /**
     * 租户套餐ID
     */
    @NotNull(message = "租户套餐编号不能为空")
    private Long tenantPackageId;

    /**
     * 租户过期时间
     */
    @NotNull(message = "过期时间不能为空")
    private Date expireTime;

    /**
     * 账号数量
     */
    @NotNull(message = "账号数量不能为空")
    private Long accountCount;

    /**
     * 备注
     */
    private String remark;

}
