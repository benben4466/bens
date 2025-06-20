package cn.ibenbeni.bens.config.starter.cache;

import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.cache.redis.util.CreateRedisTemplateUtil;
import cn.ibenbeni.bens.config.modular.cache.ConfigValueRedisCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 参数配置模块缓存自动配置内存（Redis）
 *
 * @author: benben
 * @time: 2025/6/20 下午3:32
 */
@Configuration
@ConditionalOnClass(name = "org.springframework.data.redis.connection.RedisConnectionFactory")
public class ConfigRedisCacheAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "configValueCache")
    public CacheOperatorApi<String> configValueCache(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = CreateRedisTemplateUtil.createObject(redisConnectionFactory);
        return new ConfigValueRedisCache(redisTemplate);
    }

}
