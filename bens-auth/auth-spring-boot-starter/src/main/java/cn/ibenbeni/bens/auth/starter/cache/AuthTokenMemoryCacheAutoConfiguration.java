package cn.ibenbeni.bens.auth.starter.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.auth.api.constants.LoginCacheConstants;
import cn.ibenbeni.bens.auth.api.expander.AuthConfigExpander;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.auth.customize.cache.LoginErrorCountMemoryCache;
import cn.ibenbeni.bens.auth.customize.session.cache.logintoken.MemoryLoginTokenCache;
import cn.ibenbeni.bens.auth.customize.session.cache.loginuser.MemoryLoginUserCache;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.cache.api.constants.CacheConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 认证和鉴权模块的自动配置
 *
 * @author benben
 * @date 2025/5/22  下午9:06
 */
@Configuration
@ConditionalOnMissingClass("org.springframework.data.redis.connection.RedisConnectionFactory")
public class AuthTokenMemoryCacheAutoConfiguration {

    /**
     * 登录用户的缓存，默认使用内存方式
     * <p>如需redis，可在项目创建一个名为 loginUserCache 的bean替代即可</p>
     */
    @Bean
    public CacheOperatorApi<LoginUser> loginUserCache() {
        Long sessionExpiredSeconds = AuthConfigExpander.getSessionExpiredSeconds();
        TimedCache<String, LoginUser> timedCache = CacheUtil.newTimedCache(sessionExpiredSeconds * 1000);
        return new MemoryLoginUserCache(timedCache);
    }

    /**
     * 登陆用户Token缓存，默认使用内存方式
     * <p>如需redis，可在项目创建一个名为loginTokenCache的bean替代即可</p>
     */
    @Bean
    public CacheOperatorApi<String> loginTokenCache() {
        TimedCache<String, String> loginTimeCache = CacheUtil.newTimedCache(CacheConstants.NONE_EXPIRED_TIME * 1000);
        return new MemoryLoginTokenCache(loginTimeCache);
    }

    /**
     * 登录错误次数的缓存
     * <p>注入Bean</p>
     */
    @Bean
    public CacheOperatorApi<Integer> loginErrorCountCacheApi() {
        TimedCache<String, Integer> loginTimeCache = CacheUtil.newTimedCache(LoginCacheConstants.LOGIN_CACHE_TIMEOUT_SECONDS * 1000);
        return new LoginErrorCountMemoryCache(loginTimeCache);
    }

}
