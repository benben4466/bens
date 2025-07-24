package cn.ibenbeni.bens.sys.modular.permission.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

@Data
@Schema(description = "管理后台 - 赋予用户角色入参")
public class PermissionAssignUserRoleReq {

    @Schema(description = "用户ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "角色ID列表", example = "1,3,5")
    private Set<Long> roleIds = Collections.emptySet();

}
