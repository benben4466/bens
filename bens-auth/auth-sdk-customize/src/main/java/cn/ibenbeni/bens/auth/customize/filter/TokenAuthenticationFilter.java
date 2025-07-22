package cn.ibenbeni.bens.auth.customize.filter;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.auth.api.context.LoginUserHolder;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.auth.api.util.CommonLoginUserUtils;
import cn.ibenbeni.bens.auth.customize.pojo.payload.DefaultJwtPayload;
import cn.ibenbeni.bens.auth.customize.token.TokenService;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.rule.util.DateUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token过滤器，验证Token有效性
 * 验证通过，构造 {@link LoginUser} 对象，设置到
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    public static final String NAME = "TokenAuthenticationFilter";

    @Resource
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userToken = CommonLoginUserUtils.getToken();
        if (StrUtil.isNotBlank(userToken)) {
            LoginUser loginUser = buildLoginUserByToken(userToken);

            // 设置到登陆用户上下文中
            if (loginUser != null) {
                LoginUserHolder.set(loginUser);
            }
        }

        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }

    private LoginUser buildLoginUserByToken(String userToken) {
        try {
            DefaultJwtPayload defaultJwtPayload = tokenService.validateAccessToken(userToken);
            if (defaultJwtPayload == null) {
                return null;
            }

            // 构建LoginUser对象
            return LoginUser.builder()
                    .userId(defaultJwtPayload.getUserId())
                    .account(defaultJwtPayload.getAccount())
                    .token(userToken)
                    .expiresTime(DateUtils.convertLocalDateTime(defaultJwtPayload.getExpirationTimestamp()))
                    .build();
        } catch (ServiceException ex) {
            // 校验 Token 不通过时，考虑到一些接口是无需登录的，所以直接返回 null 即可
            return null;
        }
    }

}
