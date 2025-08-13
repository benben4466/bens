package cn.ibenbeni.bens.tenant.modular.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseBusinessEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 租户实体
 * <p>该表要忽略租户</p>
 *
 * @author: benben
 * @time: 2025/7/1 上午10:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_tenant", autoResultMap = true)
public class TenantDO extends BaseBusinessEntity {

    /**
     * 系统租户ID
     */
    @TableField(exist = false)
    public static final Long PACKAGE_ID_SYSTEM = 0L;

    /**
     * 租户ID
     */
    @TableId(value = "tenant_id", type = IdType.ASSIGN_ID)
    private Long tenantId;

    /**
     * 租户名称
     */
    @TableField("tenant_name")
    private String tenantName;

    /**
     * 租户管理用户ID
     */
    @TableField(value = "contact_user_id")
    private Long contactUserId;

    /**
     * 租户联系人名称
     */
    @TableField("contact_name")
    private String contactName;

    /**
     * 租户联系人手机号码
     */
    @TableField("contact_mobile")
    private String contactMobile;

    /**
     * 租户状态
     * <p>1=正常，2=禁用</p>
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 租户绑定的域名
     */
    @TableField("tenant_website")
    private String tenantWebsite;

    /**
     * 租户套餐ID
     */
    @TableField("tenant_package_id")
    private Long tenantPackageId;

    /**
     * 租户过期时间
     */
    @TableField("expire_time")
    private Date expireTime;

    /**
     * 授权的账号数量
     */
    @TableField("account_count")
    private Long accountCount;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
