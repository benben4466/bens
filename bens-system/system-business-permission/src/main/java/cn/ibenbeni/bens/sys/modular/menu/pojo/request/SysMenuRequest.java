package cn.ibenbeni.bens.sys.modular.menu.pojo.request;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private Long menuId;

    /**
     * 父ID
     * <p>顶级节点的父ID是-1</p>
     */
    private Long menuParentId;

    /**
     * 父ID集合
     */
    private String menuPids;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单类型
     * <p>10-后台菜单，20-纯前台路由界面，30-内部链接，40-外部链接</p>
     */
    private Integer menuType;

    /**
     * 显示排序
     */
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
    private List<SysMenu> updateMenuTree;

}
