package cn.ibenbeni.bens.sys.api.pojo.role.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * 角色信息-DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 显示顺序
     */
    private BigDecimal roleSort;

    /**
     * 数据范围类型
     * <p>10-仅本人数据，20-本部门数据，30-本部门及以下数据，40-指定部门数据，50-全部数据</p>
     */
    private Integer dataScopeType;

    /**
     * 数据权限部门ID集合(数据范围类型:指定部门数据时使用)
     * <p>JSON数组</p>
     */
    private Set<Long> dataScopeDeptIds;

    /**
     * 状态：1-启用，2-禁用
     */
    private Integer statusFlag;

    /**
     * 角色类型
     * <p>枚举值：{@link cn.ibenbeni.bens.sys.api.enums.role.RoleTypeEnum}</p>
     */
    private Integer roleType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private Long updateUser;

}
