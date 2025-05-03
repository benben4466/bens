package cn.ibenbeni.bens.cache.memory.starter;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.api.constants.CacheConstants;
import cn.ibenbeni.bens.cache.memory.operator.DefaultMemoryCacheOperator;
import cn.ibenbeni.bens.cache.memory.operator.DefaultStringMemoryCacheOperator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基于内存缓存的默认配置
 *
 * @author benben
 * @date 2025/5/3  下午3:48
 */
@Configuration
public class ProjectMemoryCacheAutoConfiguration {

    /**
     * 创建默认的value是string类型的内存缓存
     */
    @Bean
    @ConditionalOnMissingBean(name = "defaultStringCacheOperator")
    public DefaultStringMemoryCacheOperator defaultStringCacheOperator() {
        TimedCache<String, String> stringTimedCache = CacheUtil.newTimedCache(CacheConstants.DEFAULT_CACHE_TIMEOUT);
        return new DefaultStringMemoryCacheOperator(stringTimedCache);
    }

    /**
     * 创建默认的value是object类型的内存缓存
     */
    @Bean
    @ConditionalOnMissingBean(name = "defaultMemoryCacheOperator")
    public DefaultMemoryCacheOperator defaultMemoryCacheOperator() {
        TimedCache<String, Object> objectTimedCache = CacheUtil.newTimedCache(CacheConstants.DEFAULT_CACHE_TIMEOUT);
        return new DefaultMemoryCacheOperator(objectTimedCache);
    }

}
