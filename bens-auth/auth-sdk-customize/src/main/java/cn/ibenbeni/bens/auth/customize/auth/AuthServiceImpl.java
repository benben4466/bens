package cn.ibenbeni.bens.auth.customize.auth;

import cn.ibenbeni.bens.auth.api.AuthServiceApi;
import cn.ibenbeni.bens.auth.api.SessionManagerApi;
import cn.ibenbeni.bens.auth.api.context.LoginContext;
import cn.ibenbeni.bens.auth.api.exception.AuthException;
import cn.ibenbeni.bens.auth.api.exception.enums.AuthExceptionEnum;
import cn.ibenbeni.bens.auth.api.pojo.auth.AuthLoginReq;
import cn.ibenbeni.bens.auth.api.pojo.auth.AuthLoginResp;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.auth.api.pojo.payload.DefaultJwtPayload;
import cn.ibenbeni.bens.auth.customize.token.TokenService;
import cn.ibenbeni.bens.jwt.api.JwtApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 认证服务的实现
 *
 * @author benben
 */
@Service
public class AuthServiceImpl implements AuthServiceApi {

    // region 属性

    @Resource
    private JwtApi jwtApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private TokenService tokenService;

    @Resource
    private LoginService loginService;

    // endregion

    // region 公共方法

    @Override
    public AuthLoginResp login(AuthLoginReq loginReq) {
        return loginService.loginAction(loginReq);
    }

    @Override
    public void logout() {
        String userToken = LoginContext.me().getToken();
        logoutWithToken(userToken);
    }

    @Override
    public void logoutWithToken(String token) {
        sessionManagerApi.removeSession(token);
    }

    @Override
    public DefaultJwtPayload validateToken(String token) throws AuthException {
        try {
            // 校验Token是否过去及自身正确性
            jwtApi.validateTokenWithException(token);
            // 获取JWT载荷
            DefaultJwtPayload defaultPayload = tokenService.getDefaultPayload(token);
            // 校验Token是否已退出登陆
            LoginUser session = sessionManagerApi.getSession(token);
            if (session == null) {
                throw new AuthException(AuthExceptionEnum.AUTH_EXPIRED_ERROR);
            }

            return defaultPayload;
        } catch (io.jsonwebtoken.JwtException jwtSelfEx) {
            throw new AuthException(AuthExceptionEnum.TOKEN_PARSE_ERROR);
        }
    }

    // endregion

    // region 私有方法

    // endregion


}
