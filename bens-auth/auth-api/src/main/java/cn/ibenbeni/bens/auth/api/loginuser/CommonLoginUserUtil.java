package cn.ibenbeni.bens.auth.api.loginuser;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.auth.api.constants.AuthConstants;
import cn.ibenbeni.bens.auth.api.exception.AuthException;
import cn.ibenbeni.bens.auth.api.exception.enums.AuthExceptionEnum;
import cn.ibenbeni.bens.rule.util.HttpServletUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 获取当前登陆用户的相关方法
 *
 * @author benben
 * @date 2025/5/20  下午2:14
 */
public class CommonLoginUserUtil {

    /**
     * 获取当前登陆用户Token
     */
    public static String getToken() {
        // 获取当前HTTP请求
        HttpServletRequest request = HttpServletUtil.getRequest();

        // 1.优先从param参数中获取token
        String parameterToken = request.getParameter(AuthConstants.DEFAULT_AUTH_PARAM_NAME);
        // 不为空则直接返回param的token
        if (parameterToken != null) {
            return parameterToken;
        }

        // 2.从cookie中获取token
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AuthConstants.DEFAULT_AUTH_COOKIE_NAME)) {
                    return cookie.getValue();
                }
            }
        }

        // 3. 从header中获取token
        String authToken = request.getHeader(AuthConstants.DEFAULT_AUTH_HEADER_NAME);
        if (StrUtil.isNotBlank(authToken)) {
            return authToken;
        }

        // 获取不到token，直接告诉用户
        throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
    }

}
