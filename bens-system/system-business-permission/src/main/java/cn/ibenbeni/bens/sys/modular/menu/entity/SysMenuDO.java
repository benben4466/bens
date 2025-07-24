package cn.ibenbeni.bens.sys.modular.menu.entity;

import cn.ibenbeni.bens.db.api.pojo.entity.BaseExpandFieldEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 系统菜单实例类
 *
 * @author: benben
 * @time: 2025/5/30 下午2:08
 */
@TableName(value = "sys_menu", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuDO extends BaseExpandFieldEntity {

    /**
     * 主键
     */
    @TableId(value = "menu_id", type = IdType.ASSIGN_ID)
    private Long menuId;

    /**
     * 父ID
     * <p>顶级节点的父ID是-1</p>
     */
    @TableField("menu_parent_id")
    private Long menuParentId;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 权限编码
     */
    @TableField("permission_code")
    private String permissionCode;

    /**
     * 菜单类型
     * <p>枚举值: {@link cn.ibenbeni.bens.sys.api.enums.menu.MenuTypeEnum}</p>
     */
    @TableField("menu_type")
    private Integer menuType;

    /**
     * 显示排序
     */
    @TableField("menu_sort")
    private BigDecimal menuSort;

    /**
     * 组件地址
     */
    @TableField("component_path")
    private String componentPath;

    /**
     * 路由地址
     * <p>浏览器显示的URL，例如/menu</p>
     * <p>若为http(s)时，为外链</p>
     */
    @TableField("component_router")
    private String componentRouter;

    /**
     * 图标编码
     * <p>默认：icon-default</p>
     */
    @TableField("component_icon")
    private String componentIcon;

    /**
     * 是否可见
     * <p>枚举值: {@link cn.ibenbeni.bens.rule.enums.YesOrNotEnum}</p>
     */
    @TableField("component_visible")
    private String componentVisible;

    /**
     * 是否缓存
     * <p>注意: 使用Vue路由的keep-alive特性</p>
     * <p>枚举值: {@link cn.ibenbeni.bens.rule.enums.YesOrNotEnum}</p>
     */
    @TableField("keep_alive")
    private String keepAlive;

    /**
     * 是否总是显示
     * <p>若为false时 且 当菜单仅有一个子菜单时, 不展示自己，而直接展示子菜单</p>
     * <p>枚举值: {@link cn.ibenbeni.bens.rule.enums.YesOrNotEnum}</p>
     */
    @TableField("always_show")
    private String alwaysShow;

    /**
     * 状态
     * <p>1-启用，2-禁用</p>
     */
    @TableField("status_flag")
    private Integer statusFlag;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
