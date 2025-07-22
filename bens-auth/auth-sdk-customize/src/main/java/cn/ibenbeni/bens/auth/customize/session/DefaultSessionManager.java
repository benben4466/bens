package cn.ibenbeni.bens.auth.customize.session;

import cn.ibenbeni.bens.auth.api.SessionManagerApi;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import lombok.AllArgsConstructor;

/**
 * 基于redis的会话管理
 *
 * @author benben
 * @date 2025/5/20  下午2:47
 */
@AllArgsConstructor
public class DefaultSessionManager implements SessionManagerApi {

    /**
     * 登录用户缓存
     * <p>key=用户Token；Value=LoginUser </p>
     */
    private final CacheOperatorApi<LoginUser> loginUserCache;

    /**
     * 用户Token的缓存
     */
    private final CacheOperatorApi<String> loginTokenCache;

    /**
     * Session超时时间
     */
    private final Long sessionExpiredSeconds;

    @Override
    public void createSession(LoginUser loginUser) {
        // 缓存登陆用户信息
        loginUserCache.put(loginUser.getToken(), loginUser, sessionExpiredSeconds);
        // 缓存用户Token
        loginTokenCache.put(loginUser.getUserId().toString(), loginUser.getToken());
    }

    @Override
    public void removeSession(String token) {
        LoginUser loginUser = loginUserCache.get(token);
        // 删除登陆用户缓存
        loginUserCache.remove(token);
        // 删除用户Token缓存
        loginTokenCache.remove(loginUser.getUserId().toString());
    }

    @Override
    public LoginUser getSession(String token) {
        return loginUserCache.get(token);
    }

}
