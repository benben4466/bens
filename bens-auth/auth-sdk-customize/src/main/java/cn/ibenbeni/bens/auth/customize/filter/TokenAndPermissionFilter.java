package cn.ibenbeni.bens.auth.customize.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.auth.api.exception.AuthException;
import cn.ibenbeni.bens.auth.api.exception.enums.AuthExceptionEnum;
import cn.ibenbeni.bens.auth.customize.factory.PermissionVerificationFactory;
import cn.ibenbeni.bens.auth.customize.pojo.permission.MethodPermissionVerification;
import cn.ibenbeni.bens.resource.api.exception.ResourceException;
import cn.ibenbeni.bens.resource.api.exception.enums.ResourceExceptionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.ServletRequestPathUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限过滤器
 */
@Slf4j
@RequiredArgsConstructor
public class TokenAndPermissionFilter extends OncePerRequestFilter {

    public static final String NAME = "PermissionFilter";

    /**
     * 请求映射处理器映射
     * <p>可通过URL找到具体执行的接口</p>
     */
    private final RequestMappingHandlerMapping handlerMapping;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            // SpringBoot 2.6 起，Spring 默认启用了新的路径解析机制。PathPatternParser匹配方式（以前是 AntPathMatcher）
            // 在RequestMappingHandlerMapping#getHandler方法调用前，必须通过 ServletRequestPathUtils.parseAndCache() 方法解析路径否则报错
            ServletRequestPathUtils.parseAndCache(request);
            HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(request);
            if (handlerExecutionChain != null) {
                Object handler = handlerExecutionChain.getHandler();
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    // 构造MethodPermissionVerification对象
                    // 1.先获取类上资源注解填充
                    // 2.再获取方法上资源注解填充
                    MethodPermissionVerification permissionVerification = new MethodPermissionVerification();
                    PermissionVerificationFactory.clazzFillPermissionVerification(handlerMethod.getBeanType(), permissionVerification);
                    PermissionVerificationFactory.methodFillPermissionVerification(handlerMethod.getMethod(), permissionVerification);

                    // MethodPermissionVerification所有属性为空，则说明未使用资源接口
                    if (BeanUtil.isEmpty(permissionVerification)) {
                        throw new AuthException(AuthExceptionEnum.NOT_USED_RESOURCE_INTERFACE);
                    }

                    // 校验
                    PermissionVerificationFactory.permissionVerification(permissionVerification);

                    log.debug("请求接口类: {}", handlerMethod.getBeanType().getName());
                    log.debug("请求接口方法: {}", handlerMethod.getMethod().getName());
                } else {
                    // 非自定义Controller资源，如静态资源、默认错误处理等
                    // 忽略处理
                }
            } else {
                // 未存在该资源
                throw new ResourceException(ResourceExceptionEnum.RESOURCE_NOT_EXIST);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }

}
