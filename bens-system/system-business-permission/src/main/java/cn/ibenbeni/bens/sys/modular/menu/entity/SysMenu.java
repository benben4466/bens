package cn.ibenbeni.bens.sys.modular.menu.entity;

import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.db.api.pojo.entity.BaseExpandFieldEntity;
import cn.ibenbeni.bens.rule.tree.buildpids.BasePidBuildModel;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 系统菜单实例类
 *
 * @author: benben
 * @time: 2025/5/30 下午2:08
 */
@TableName(value = "sys_menu", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseExpandFieldEntity implements BasePidBuildModel {

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
     * 父ID集合
     */
    @TableField("menu_pids")
    private String menuPids;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 菜单编码
     */
    @TableField("menu_code")
    private String menuCode;

    /**
     * 菜单类型
     * <p>10-后台菜单，20-纯前台路由界面，30-内部链接，40-外部链接</p>
     */
    @TableField("menu_type")
    private Integer menuType;

    /**
     * 显示排序
     */
    @TableField("menu_sort")
    private BigDecimal menuSort;

    /**
     * 前端组件名
     */
    @TableField("antdv_component")
    private String antdvComponent;

    /**
     * 路由地址
     * <p>浏览器显示的URL，例如/menu</p>
     */
    @TableField("antdv_router")
    private String antdvRouter;

    /**
     * 图标编码
     * <p>默认：icon-default</p>
     */
    @TableField("antdv_icon")
    private String antdvIcon;

    /**
     * 外部链接地址
     */
    @TableField("antdv_link_url")
    private String antdvLinkUrl;

    /**
     * 用于非菜单显示页面的重定向url设置
     */
    @TableField("antdv_active_url")
    private String antdvActiveUrl;

    /**
     * 是否可见(分离版用)
     * <p>Y-是，N-否</p>
     */
    @TableField("antdv_visible")
    private String antdvVisible;

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

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;

    /**
     * 当前菜单的子菜单
     * <p>这个参数一般用在更新菜单树的顺序和上下级结构中</p>
     */
    @TableField(exist = false)
    private List<SysMenu> children;

    /**
     * 父级菜单名称
     */
    @TableField(exist = false)
    private String menuParentName;

    @Override
    public String pidBuildNodeId() {
        if (ObjectUtil.isEmpty(menuId)) {
            return "";
        }
        return menuId.toString();
    }

    @Override
    public String pidBuildParentId() {
        if (ObjectUtil.isEmpty(menuParentId)) {
            return "";
        }
        return menuParentId.toString();
    }

    @Override
    public void setPidBuildPidStructure(String pids) {
        this.menuPids = pids;
    }

}
