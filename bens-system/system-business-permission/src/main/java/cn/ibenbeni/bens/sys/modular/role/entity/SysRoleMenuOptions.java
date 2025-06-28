package cn.ibenbeni.bens.sys.modular.role.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和菜单下的功能关联实例类
 *
 * @author: benben
 * @time: 2025/6/8 下午4:27
 */
@TableName(value = "sys_role_menu_options", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleMenuOptions extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "role_menu_option_id", type = IdType.ASSIGN_ID)
    private Long roleMenuOptionId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 菜单功能ID
     */
    @TableField("menu_option_id")
    private Long menuOptionId;

    /**
     * 功能所属的菜单ID
     * <p>说明：冗余字段</p>
     */
    @TableField("menu_id")
    private Long menuId;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

}
