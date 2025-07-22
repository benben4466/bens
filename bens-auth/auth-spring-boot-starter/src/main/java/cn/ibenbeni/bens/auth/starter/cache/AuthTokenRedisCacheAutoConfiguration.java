package cn.ibenbeni.bens.auth.starter.cache;

import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.auth.customize.cache.LoginErrorCountRedisCache;
import cn.ibenbeni.bens.auth.customize.session.cache.logintoken.RedisLoginTokenCache;
import cn.ibenbeni.bens.auth.customize.session.cache.loginuser.RedisLoginUserCache;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.cache.redis.util.CreateRedisTemplateUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 认证和鉴权模块的自动配置
 *
 * @author benben
 * @date 2025/5/22  下午9:21
 */
@Configuration
@ConditionalOnClass(name = "org.springframework.data.redis.connection.RedisConnectionFactory")
public class AuthTokenRedisCacheAutoConfiguration {

    /**
     * 登录用户的缓存，默认使用内存方式
     */
    @Bean
    public CacheOperatorApi<LoginUser> loginUserCache(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, LoginUser> redisTemplate = CreateRedisTemplateUtil.createObject(redisConnectionFactory);
        return new RedisLoginUserCache(redisTemplate);
    }

    /**
     * 登录用户Token的缓存，默认使用内存方式
     */
    @Bean
    public CacheOperatorApi<String> loginTokenCache(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = CreateRedisTemplateUtil.createObject(redisConnectionFactory);
        return new RedisLoginTokenCache(redisTemplate);
    }

    /**
     * 登录错误次数的缓存
     */
    @Bean
    public CacheOperatorApi<Integer> loginErrorCountCacheApi(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Integer> redisTemplate = CreateRedisTemplateUtil.createObject(redisConnectionFactory);
        return new LoginErrorCountRedisCache(redisTemplate);
    }

}
