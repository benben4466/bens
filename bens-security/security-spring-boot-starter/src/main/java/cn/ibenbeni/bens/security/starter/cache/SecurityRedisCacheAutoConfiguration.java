package cn.ibenbeni.bens.security.starter.cache;

import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.cache.redis.util.CreateRedisTemplateUtil;
import cn.ibenbeni.bens.security.captcha.cache.CaptchaRedisCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 安全模块，缓存的依赖
 *
 * @author benben
 * @date 2025/5/25  下午2:47
 */
@Configuration
@ConditionalOnClass(name = "org.springframework.data.redis.connection.RedisConnectionFactory")
public class SecurityRedisCacheAutoConfiguration {

    /**
     * 验证码相关的缓存，Redis缓存
     */
    @Bean("captchaCache")
    public CacheOperatorApi<String> captchaRedisCache(RedisConnectionFactory redisConnectionFactory) {
        // 验证码过期时间 120秒
        RedisTemplate<String, String> redisTemplate = CreateRedisTemplateUtil.createString(redisConnectionFactory);
        return new CaptchaRedisCache(redisTemplate);
    }

}
