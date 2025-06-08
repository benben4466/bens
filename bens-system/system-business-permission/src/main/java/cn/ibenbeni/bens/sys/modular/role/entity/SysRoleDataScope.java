package cn.ibenbeni.bens.sys.modular.role.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色数据范围实例类
 *
 * @author: benben
 * @time: 2025/6/8 上午11:34
 */
@TableName(value = "sys_role_data_scope", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleDataScope extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "role_data_scope_id", type = IdType.ASSIGN_ID)
    private Long roleDataScopeId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 组织ID
     */
    @TableField("organization_id")
    private Long organizationId;

}
