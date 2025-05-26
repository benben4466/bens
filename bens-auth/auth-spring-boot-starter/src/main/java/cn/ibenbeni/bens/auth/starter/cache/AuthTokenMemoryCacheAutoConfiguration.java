package cn.ibenbeni.bens.auth.starter.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.auth.api.constants.LoginCacheConstants;
import cn.ibenbeni.bens.auth.cache.LoginErrorCountMemoryCache;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
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
     * 登录错误次数的缓存
     * <p>注入Bean</p>
     */
    @Bean
    public CacheOperatorApi<Integer> loginErrorCountCacheApi() {
        TimedCache<String, Integer> loginTimeCache = CacheUtil.newTimedCache(LoginCacheConstants.LOGIN_CACHE_TIMEOUT_SECONDS * 1000);
        return new LoginErrorCountMemoryCache(loginTimeCache);
    }

}
