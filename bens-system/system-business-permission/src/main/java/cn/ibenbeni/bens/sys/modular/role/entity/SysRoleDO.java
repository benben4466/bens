package cn.ibenbeni.bens.sys.modular.role.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseExpandFieldEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Set;

/**
 * 系统角色实例类
 *
 * @author benben
 * @date 2025/5/3  下午10:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role", autoResultMap = true)
public class SysRoleDO extends BaseExpandFieldEntity {

    /**
     * 主键ID
     */
    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 显示顺序
     */
    @TableField("role_sort")
    private BigDecimal roleSort;

    /**
     * 数据范围类型
     * <p>10-仅本人数据，20-本部门数据，30-本部门及以下数据，40-指定部门数据，50-全部数据</p>
     */
    @TableField("data_scope_type")
    private Integer dataScopeType;

    /**
     * 数据权限部门ID集合(数据范围类型:指定部门数据时使用)
     * <p>JSON数组</p>
     */
    @TableField(value = "data_scope_dept_ids", typeHandler = JacksonTypeHandler.class)
    private Set<Long> dataScopeDeptIds;

    /**
     * 状态：1-启用，2-禁用
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 角色类型
     * <p>枚举值：{@link cn.ibenbeni.bens.sys.api.enums.role.RoleTypeEnum}</p>
     */
    @TableField("role_type")
    private Integer roleType;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

}
