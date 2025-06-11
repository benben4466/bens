package cn.ibenbeni.bens.sys.modular.role.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色权限限制实例类
 *
 * @author: benben
 * @time: 2025/6/9 下午10:30
 */
@TableName(value = "sys_role_limit", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleLimit extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "role_limit_id", type = IdType.ASSIGN_ID)
    private Long roleLimitId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 角色限制类型
     * <p>1=角色可分配的菜单；2=角色可分配的功能</p>
     */
    @TableField("limit_type")
    private Integer limitType;

    /**
     * 业务ID
     * <p>菜单ID或菜单功能ID</p>
     */
    @TableField("business_id")
    private Long businessId;

}
