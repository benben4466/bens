package cn.ibenbeni.bens.auth.customize.factory;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.auth.api.AuthServiceApi;
import cn.ibenbeni.bens.auth.api.context.LoginUserHolder;
import cn.ibenbeni.bens.auth.api.exception.AuthException;
import cn.ibenbeni.bens.auth.api.exception.enums.AuthExceptionEnum;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.auth.api.pojo.payload.DefaultJwtPayload;
import cn.ibenbeni.bens.auth.api.util.CommonLoginUserUtils;
import cn.ibenbeni.bens.auth.customize.pojo.permission.MethodPermissionVerification;
import cn.ibenbeni.bens.resource.api.annotation.*;
import cn.ibenbeni.bens.rule.util.DateUtils;
import cn.ibenbeni.bens.sys.api.PermissionApi;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限校验工厂
 */
public class PermissionVerificationFactory {

    /**
     * 类注解填充
     *
     * @param clazz        类
     * @param verification 权限校验对象
     */
    public static void clazzFillPermissionVerification(Class<?> clazz, MethodPermissionVerification verification) {
        // 类上@ApiResource注解
        ApiResource apiResource = AnnotationUtils.findAnnotation(clazz, ApiResource.class);
        if (apiResource != null) {
            String path = apiResource.path().length > 0 ? apiResource.path()[0] : null;
            fillPermissionVerification(
                    verification,
                    apiResource.requiredLogin(),
                    apiResource.requiredPermission(),
                    apiResource.requirePermissionCode(),
                    path
            );
        }
    }

    /**
     * 方法注解填充
     * <p>只解析一个资源注解</p>
     *
     * @param method       方法
     * @param verification 权限校验对象
     */
    public static void methodFillPermissionVerification(Method method, MethodPermissionVerification verification) {
        ApiResource apiResource = AnnotationUtils.findAnnotation(method, ApiResource.class);
        if (apiResource != null) {
            String path = apiResource.path().length > 0 ? apiResource.path()[0] : null;
            fillPermissionVerification(
                    verification,
                    apiResource.requiredLogin(),
                    apiResource.requiredPermission(),
                    apiResource.requirePermissionCode(),
                    path
            );
            return;
        }

        GetResource getResource = AnnotationUtils.findAnnotation(method, GetResource.class);
        if (getResource != null) {
            String path = getResource.path().length > 0 ? getResource.path()[0] : null;
            fillPermissionVerification(
                    verification,
                    getResource.requiredLogin(),
                    getResource.requiredPermission(),
                    getResource.requirePermissionCode(),
                    path
            );
            return;
        }


        PostResource postResource = AnnotationUtils.findAnnotation(method, PostResource.class);
        if (postResource != null) {
            String path = postResource.path().length > 0 ? postResource.path()[0] : null;
            fillPermissionVerification(
                    verification,
                    postResource.requiredLogin(),
                    postResource.requiredPermission(),
                    postResource.requirePermissionCode(),
                    path
            );
            return;
        }

        PutResource putResource = AnnotationUtils.findAnnotation(method, PutResource.class);
        if (putResource != null) {
            String path = putResource.path().length > 0 ? putResource.path()[0] : null;
            fillPermissionVerification(
                    verification,
                    putResource.requiredLogin(),
                    putResource.requiredPermission(),
                    putResource.requirePermissionCode(),
                    path
            );
            return;
        }

        DeleteResource deleteResource = AnnotationUtils.findAnnotation(method, DeleteResource.class);
        if (deleteResource != null) {
            String path = deleteResource.path().length > 0 ? deleteResource.path()[0] : null;
            fillPermissionVerification(
                    verification,
                    deleteResource.requiredLogin(),
                    deleteResource.requiredPermission(),
                    deleteResource.requirePermissionCode(),
                    path
            );
            return;
        }
    }

    /**
     * 填充权限验证信息
     *
     * @param verification          权限验证对象
     * @param requiredLogin         是否需要登陆
     * @param requiredPermission    是否需要校验权限
     * @param requirePermissionCode 权限编码
     * @param path                  请求路径
     */
    public static void fillPermissionVerification(MethodPermissionVerification verification, boolean requiredLogin, boolean requiredPermission, String requirePermissionCode, String path) {
        verification.setRequiredLogin(requiredLogin);
        verification.setRequiredPermission(requiredPermission);

        // 权限编码
        List<String> newRequirePermissionCode = ObjectUtil.defaultIfNull(verification.getRequirePermissionCode(), new ArrayList<>());
        if (StrUtil.isNotBlank(requirePermissionCode)) {
            newRequirePermissionCode.add(requirePermissionCode);
            verification.setRequirePermissionCode(newRequirePermissionCode);
        }

        // 设置请求路径
        if (StrUtil.isNotBlank(path)) {
            String frontPath = ObjectUtil.defaultIfBlank(verification.getPath(), StrUtil.EMPTY);
            StringBuilder pathStr = new StringBuilder(frontPath);
            if (path.startsWith(StrUtil.SLASH)) {
                pathStr.append(StrUtil.SLASH);
            }
            pathStr.append(path);
            verification.setPath(pathStr.toString());
        }
    }

    /**
     * 权限校验
     *
     * @param verification 权限校验信息
     */
    public static void permissionVerification(MethodPermissionVerification verification) {
        String userToken = null;
        if (verification.getRequiredLogin()) {
            try {
                userToken = CommonLoginUserUtils.getToken();
            } catch (Exception ex) {
                throw new AuthException(AuthExceptionEnum.USER_NOT_LOGIN);
            }
            if (StrUtil.isNotBlank(userToken)) {
                LoginUser loginUser = buildLoginUserByToken(userToken);
                // 设置到登陆用户上下文中
                if (loginUser != null) {
                    LoginUserHolder.set(loginUser);
                }
            }
        }
        if (verification.getRequiredPermission()) {
            // 需要校验权限的肯定是需要登陆的
            if (StrUtil.isBlank(userToken)) {
                throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
            }
            // 校验权限编码
            PermissionApi permissionApi = SpringUtil.getBean(PermissionApi.class);
            permissionApi.validatePermission(userToken, verification.getRequirePermissionCode());
        }

        // TODO 请求路径白名单
    }

    public static LoginUser buildLoginUserByToken(String userToken) {
        AuthServiceApi authServiceApi = SpringUtil.getBean(AuthServiceApi.class);
        DefaultJwtPayload defaultJwtPayload = authServiceApi.validateToken(userToken);
        if (defaultJwtPayload == null) {
            throw new AuthException(AuthExceptionEnum.TOKEN_PARSE_ERROR);
        }
        // 构建LoginUser对象
        return LoginUser.builder()
                .userId(defaultJwtPayload.getUserId())
                .account(defaultJwtPayload.getAccount())
                .token(userToken)
                .expiresTime(DateUtils.convertLocalDateTime(defaultJwtPayload.getExpirationTimestamp()))
                .build();
    }

}
