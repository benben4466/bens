package cn.ibenbeni.bens.config.starter.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.config.api.constants.ConfigConstants;
import cn.ibenbeni.bens.config.modular.cache.ConfigValueMemoryCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 参数配置缓存自动配置（内存）
 *
 * @author: benben
 * @time: 2025/6/20 下午3:31
 */
@Configuration
@ConditionalOnMissingClass("org.springframework.data.redis.connection.RedisConnectionFactory")
public class ConfigMemoryCacheAutoConfiguration {

    /**
     * 注入参数配置值缓存
     */
    @Bean
    @ConditionalOnMissingBean(name = "configValueCache")
    public CacheOperatorApi<String> configValueCache() {
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(1000 * ConfigConstants.DEFAULT_CONFIG_CACHE_TIMEOUT_SECONDS);
        return new ConfigValueMemoryCache(timedCache);
    }

}
