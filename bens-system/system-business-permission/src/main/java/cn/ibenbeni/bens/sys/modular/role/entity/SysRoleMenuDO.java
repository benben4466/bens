package cn.ibenbeni.bens.sys.modular.role.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

/**
 * 角色菜单关联实例类
 *
 * @author: benben
 * @time: 2025/6/3 下午9:16
 */
@TableName(value = "sys_role_menu", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysRoleMenuDO extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "role_menu_id", type = IdType.ASSIGN_ID)
    private Long roleMenuId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableField("menu_id")
    private Long menuId;

}
