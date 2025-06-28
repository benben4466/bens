package cn.ibenbeni.bens.sys.modular.menu.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单下的功能操作实例类
 *
 * @author: benben
 * @time: 2025/6/2 上午9:48
 */
@TableName(value = "sys_menu_options", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuOptions extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "menu_option_id", type = IdType.ASSIGN_ID)
    private Long menuOptionId;

    /**
     * 菜单ID
     */
    @TableField("menu_id")
    private Long menuId;

    /**
     * 功能或操作的名称
     */
    @TableField("option_name")
    private String optionName;

    /**
     * 功能或操作的编码
     */
    @TableField("option_code")
    private String optionCode;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

}
