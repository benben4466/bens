package cn.ibenbeni.bens.auth.api.pojo.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "管理后台 - 账号密码登录入参")
public class AuthLoginReq {

    @NotBlank(message = "账号不能为空")
    @Schema(description = "账号", example = "benben", requiredMode = Schema.RequiredMode.REQUIRED)
    private String account;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
