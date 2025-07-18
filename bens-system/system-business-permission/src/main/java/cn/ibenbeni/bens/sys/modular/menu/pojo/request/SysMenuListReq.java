package cn.ibenbeni.bens.sys.modular.menu.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "管理后台 - 菜单列表入参")
public class SysMenuListReq {

    @Schema(description = "菜单名称，模糊匹配", example = "笨笨菜单")
    private String menuName;

    @Schema(description = "菜单状态", example = "1")
    private Integer statusFlag;

}
