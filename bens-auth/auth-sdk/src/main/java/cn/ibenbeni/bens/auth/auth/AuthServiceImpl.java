package cn.ibenbeni.bens.auth.auth;

import cn.dev33.satoken.stp.StpUtil;
import cn.ibenbeni.bens.auth.api.AuthServiceApi;
import cn.ibenbeni.bens.auth.api.context.LoginContext;
import cn.ibenbeni.bens.auth.api.pojo.auth.LoginRequest;
import cn.ibenbeni.bens.auth.api.pojo.auth.LoginResponse;
import cn.ibenbeni.bens.log.api.LoginLogServiceApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 认证服务的实现
 *
 * @author benben
 * @date 2025/5/20  下午9:51
 */
@Service
public class AuthServiceImpl implements AuthServiceApi {

    @Resource
    private LoginService loginService;

    @Resource
    private LoginLogServiceApi loginLogServiceApi;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return loginService.loginAction(loginRequest, true);
    }

    @Override
    public void logout() {
        // TODO config模块完善后补充
        // 演示环境，不记录退出日志
        // if (!DemoConfigExpander.getDemoEnvFlag()) {
        loginLogServiceApi.loginOutSuccess(LoginContext.me().getLoginUser().getUserId());
        StpUtil.logout();
    }

}
