package cn.ibenbeni.bens.sys.modular.menu.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Schema(description = "菜单创建/修改入参")
public class SysMenuSaveReq {

    @Schema(description = "菜单ID", example = "10")
    private Long menuId;

    @NotNull(message = "父菜单ID不能为空")
    @Schema(description = "父菜单ID", example = "-1")
    private Long menuParentId;

    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    @Schema(description = "菜单名称", example = "笨笨菜单")
    private String menuName;

    @Size(max = 100, message = "权限编码长度不能超过100个字符")
    @Schema(description = "权限编码", example = "sys:user")
    private String permissionCode;

    @NotNull(message = "菜单类型不能为空")
    @Schema(description = "菜单类型", example = "1")
    private Integer menuType;

    @NotNull(message = "显示顺序不能为空")
    @Schema(description = "菜单显示排序", example = "1.0")
    private BigDecimal menuSort;

    @Size(max = 200, message = "组件地址不能超过200个字符")
    @Schema(description = "组件地址", example = "/system/auth/role/index")
    private String componentPath;

    @Size(max = 200, message = "路由地址不能超过200个字符")
    @Schema(description = "路由地址", example = "/system/auth/role")
    private String componentRouter;

    @Schema(description = "图标编码", example = "icon-default")
    private String componentIcon;

    @Schema(description = "是否可见", example = "Y")
    private String componentVisible;

    @Schema(description = "是否缓存", example = "Y")
    private String keepAlive;

    @Schema(description = "是否总是显示", example = "Y")
    private String alwaysShow;

    @NotNull(message = "状态不能为空")
    @Schema(description = "菜单状态", example = "1")
    private Integer statusFlag;

    @Schema(description = "备注", example = "笨笨备注")
    private String remark;

}
