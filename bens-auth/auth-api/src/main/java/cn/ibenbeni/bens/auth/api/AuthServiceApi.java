package cn.ibenbeni.bens.auth.api;

import cn.ibenbeni.bens.auth.api.pojo.auth.AuthLoginReq;
import cn.ibenbeni.bens.auth.api.pojo.auth.AuthLoginResp;

/**
 * 认证服务的接口，包括基本的登录退出操作和校验token等操作
 *
 * @author benben
 */
public interface AuthServiceApi {

    /**
     * 账号登录
     */
    AuthLoginResp login(AuthLoginReq loginReq);

    /**
     * 当前登录人退出登录
     */
    void logout();

    /**
     * 移除某个token，也就是退出某个用户
     *
     * @param token 用户Token
     */
    void logoutWithToken(String token);

}
