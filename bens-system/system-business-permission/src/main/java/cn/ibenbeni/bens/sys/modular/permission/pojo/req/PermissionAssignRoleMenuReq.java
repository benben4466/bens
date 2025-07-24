package cn.ibenbeni.bens.sys.modular.permission.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "管理后台 - 赋予角色菜单入参")
public class PermissionAssignRoleMenuReq {

    @Schema(description = "角色ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long roleId;

    @Schema(description = "菜单ID列表", example = "1,3,5")
    private Set<Long> menuIds;

}
