package cn.ibenbeni.bens.auth.customize.auth;

import cn.ibenbeni.bens.auth.api.AuthServiceApi;
import cn.ibenbeni.bens.auth.api.SessionManagerApi;
import cn.ibenbeni.bens.auth.api.context.LoginContext;
import cn.ibenbeni.bens.auth.api.pojo.auth.AuthLoginReq;
import cn.ibenbeni.bens.auth.api.pojo.auth.AuthLoginResp;
import cn.ibenbeni.bens.auth.api.pojo.auth.LoginRequest;
import cn.ibenbeni.bens.auth.api.pojo.auth.LoginResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 认证服务的实现
 *
 * @author benben
 */
@Service
public class AuthServiceImpl implements AuthServiceApi {

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private LoginService loginService;

    // region 待删除

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

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

    // endregion

    // region 私有方法

    // endregion


}
