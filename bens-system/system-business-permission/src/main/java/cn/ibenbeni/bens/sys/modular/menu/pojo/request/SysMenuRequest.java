package cn.ibenbeni.bens.sys.modular.menu.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 系统菜单封装类
 *
 * @author: benben
 * @time: 2025/6/1 上午11:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuRequest extends BaseRequest {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {edit.class, delete.class, detail.class})
    private Long menuId;

    /**
     * 父ID
     * <p>顶级节点的父ID是-1</p>
     */
    @NotNull(message = "父ID，顶级节点的父ID是-1不能为空", groups = {add.class})
    private Long menuParentId;

    /**
     * 父ID集合
     */
    private String menuPids;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单的名称不能为空", groups = {add.class, edit.class})
    private String menuName;

    /**
     * 菜单编码
     */
    @NotBlank(message = "菜单的编码不能为空", groups = {add.class, edit.class})
    private String menuCode;

    /**
     * 菜单类型
     * <p>10-后台菜单，20-纯前台路由界面，30-内部链接，40-外部链接</p>
     */
    @NotNull(message = "菜单类型不能为空", groups = {add.class, edit.class})
    private Integer menuType;

    /**
     * 显示排序
     */
    @NotNull(message = "排序不能为空", groups = {add.class,edit.class})
    private BigDecimal menuSort;

    /**
     * 前端组件名，也是组件代码路径
     * <p>组件代码路径为views目录下的组件路径；如：/system/structure/empower/index</p>
     */
    private String antdvComponent;

    /**
     * 路由地址
     * <p>浏览器显示的URL，例如/menu</p>
     */
    private String antdvRouter;

    /**
     * 图标编码
     * <p>默认：icon-default</p>
     */
    private String antdvIcon;

    /**
     * 外部链接地址
     */
    private String antdvLinkUrl;

    /**
     * 用于非菜单显示页面的重定向url设置
     */
    private String antdvActiveUrl;

    /**
     * 是否可见(分离版用)
     * <p>Y-是，N-否</p>
     */
    private String antdvVisible;

    /**
     * 状态
     * <p>1-启用，2-禁用</p>
     */
    private Integer statusFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用于批量操作
     */
    @NotEmpty(message = "指定应用的所有菜单集合不能为空", groups = {updateMenuTree.class})
    private List<SysMenuDO> updateMenuTree;

    // -----------------------------------------------------参数校验分组-------------------------------------------------
    // region 参数校验分组

    /**
     * 更新菜单树
     */
    public interface updateMenuTree {
    }

    // endregion

}
