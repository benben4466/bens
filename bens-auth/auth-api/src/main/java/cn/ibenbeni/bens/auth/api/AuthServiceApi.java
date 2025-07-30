package cn.ibenbeni.bens.auth.api;

import cn.ibenbeni.bens.auth.api.exception.AuthException;
import cn.ibenbeni.bens.auth.api.pojo.auth.AuthLoginReq;
import cn.ibenbeni.bens.auth.api.pojo.auth.AuthLoginResp;
import cn.ibenbeni.bens.auth.api.pojo.payload.DefaultJwtPayload;

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

    /**
     * 校验JWT Token的正确性，调用JWT工具类相关方法校验
     * <p>
     * 结果：
     * 1.JWT过期抛出异常
     * 2.Token错误抛出异常
     * 3.Token正确返回解析后数据
     * </p>
     *
     * @param token 用户Token
     * @return token解析出的用户基本信息
     * @throws AuthException 认证异常，如果token错误或过期，会有相关的异常抛出
     */
    DefaultJwtPayload validateToken(String token) throws AuthException;

}
