package cn.ibenbeni.bens.sys.modular.menu.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Schema(description = "管理后台 - 菜单信息响应")
public class MenuResp {

    @Schema(description = "菜单ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long menuId;

    /**
     * 父ID
     * <p>顶级节点的父ID是-1</p>
     */
    @Schema(description = "父菜单ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long menuParentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    @Schema(description = "菜单名称", example = "笨笨菜单", requiredMode = Schema.RequiredMode.REQUIRED)
    private String menuName;

    /**
     * 权限编码
     */
    @Size(max = 100)
    @Schema(description = "权限标识,仅菜单类型为按钮时，才需要传递", example = "sys:menu:add")
    private String permissionCode;

    /**
     * 菜单类型
     * <p>枚举值: {@link cn.ibenbeni.bens.sys.api.enums.menu.MenuTypeEnum}</p>
     */
    @NotNull(message = "菜单类型不能为空")
    @Schema(description = "菜单类型", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer menuType;

    /**
     * 显示排序
     */
    @NotNull(message = "显示顺序不能为空")
    @Schema(description = "显示顺序", example = "1.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal menuSort;

    /**
     * 组件地址
     */
    @Size(max = 200, message = "组件地址不能超过200个字符")
    @Schema(description = "组件地址", example = "/system/auth/role/index")
    private String componentPath;

    /**
     * 路由地址
     * <p>浏览器显示的URL，例如/menu</p>
     * <p>若为http(s)时，为外链</p>
     */
    @Size(max = 200, message = "路由地址不能超过200个字符")
    @Schema(description = "路由地址", example = "/system/auth/role")
    private String componentRouter;

    /**
     * 图标编码
     * <p>默认：icon-default</p>
     */
    @Schema(description = "图标编码", example = "icon-default")
    private String componentIcon;

    /**
     * 是否可见
     * <p>枚举值: {@link cn.ibenbeni.bens.rule.enums.YesOrNotEnum}</p>
     */
    @Schema(description = "是否可见", example = "Y")
    private String componentVisible;

    /**
     * 是否缓存
     * <p>注意: 使用Vue路由的keep-alive特性</p>
     * <p>枚举值: {@link cn.ibenbeni.bens.rule.enums.YesOrNotEnum}</p>
     */
    @Schema(description = "是否缓存", example = "Y")
    private String keepAlive;

    /**
     * 是否总是显示
     * <p>若为false时 且 当菜单仅有一个子菜单时, 不展示自己，而直接展示子菜单</p>
     * <p>枚举值: {@link cn.ibenbeni.bens.rule.enums.YesOrNotEnum}</p>
     */
    @Schema(description = "是否总是显示", example = "Y")
    private String alwaysShow;

    /**
     * 状态
     * <p>1-启用，2-禁用</p>
     */
    @NotNull(message = "状态不能为空")
    @Schema(description = "菜单状态", example = "1")
    private Integer statusFlag;

    /**
     * 备注
     */
    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

}
