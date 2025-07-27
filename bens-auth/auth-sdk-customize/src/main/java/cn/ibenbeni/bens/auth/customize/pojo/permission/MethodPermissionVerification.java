package cn.ibenbeni.bens.auth.customize.pojo.permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 方法权限校验参数
 * <p>{@link cn.ibenbeni.bens.resource.api.annotation.ApiResource}等注解相关</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodPermissionVerification {

    /**
     * 请求路径
     */
    private String path;

    /**
     * 是否需要登录
     */
    private Boolean requiredLogin;

    /**
     * 是否需要权限校验
     */
    private Boolean requiredPermission;

    /**
     * 需要校验的权限编码
     */
    private List<String> requirePermissionCode;

}
