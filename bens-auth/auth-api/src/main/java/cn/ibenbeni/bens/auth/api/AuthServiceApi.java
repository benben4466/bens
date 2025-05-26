package cn.ibenbeni.bens.auth.api;

import cn.ibenbeni.bens.auth.api.pojo.auth.LoginRequest;
import cn.ibenbeni.bens.auth.api.pojo.auth.LoginResponse;

/**
 * 认证服务的接口，包括基本的登录退出操作和校验token等操作
 *
 * @author benben
 */
public interface AuthServiceApi {

    /**
     * 常规登录操作
     *
     * @param loginRequest 登录的请求
     * @return token Token
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 当前登录人退出登录
     */
    void logout();

}
