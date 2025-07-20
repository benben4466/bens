package cn.ibenbeni.bens.sys.modular.user.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 用户角色关联实例类
 *
 * @author: benben
 * @time: 2025/6/2 下午2:15
 */
@TableName(value = "sys_user_role", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysUserRoleDO extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "user_role_id", type = IdType.ASSIGN_ID)
    private Long userRoleId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

}
