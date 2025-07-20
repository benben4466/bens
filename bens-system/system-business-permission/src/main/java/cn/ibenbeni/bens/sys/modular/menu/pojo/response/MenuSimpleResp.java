package cn.ibenbeni.bens.sys.modular.menu.pojo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台 - 菜单精简信息响应")
public class MenuSimpleResp {

    @Schema(description = "菜单ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long menuId;

    @Schema(description = "菜单名称", example = "笨笨菜单", requiredMode = Schema.RequiredMode.REQUIRED)
    private String menuName;

    @Schema(description = "父菜单ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long menuParentId;

    @Schema(description = "菜单类型", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer menuType;

}
