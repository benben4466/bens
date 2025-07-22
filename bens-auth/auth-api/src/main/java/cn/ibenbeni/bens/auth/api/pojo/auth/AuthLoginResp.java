package cn.ibenbeni.bens.auth.api.pojo.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "管理后台 - 登录响应")
public class AuthLoginResp {

    @Schema(description = "用户ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "用户Token", example = "benbenToken", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;

}
