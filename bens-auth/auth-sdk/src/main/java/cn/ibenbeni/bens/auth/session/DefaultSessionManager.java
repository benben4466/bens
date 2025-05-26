package cn.ibenbeni.bens.auth.session;

import cn.dev33.satoken.stp.StpUtil;
import cn.ibenbeni.bens.auth.api.SessionManagerApi;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;

import java.util.Set;

/**
 * 基于redis的会话管理
 *
 * @author benben
 * @date 2025/5/20  下午2:47
 */
public class DefaultSessionManager implements SessionManagerApi {

    /**
     * session的超时时间
     */
    private final Long sessionExpiredSeconds;

    public DefaultSessionManager(Long sessionExpiredSeconds) {
        this.sessionExpiredSeconds = sessionExpiredSeconds;
    }

    @Override
    public void createSession(String token, LoginUser loginUser) {
        StpUtil.getSession().set(token, loginUser);
    }

    @Override
    public LoginUser getSession(String token) {
        return (LoginUser) StpUtil.getSession().get(token);
    }

}
