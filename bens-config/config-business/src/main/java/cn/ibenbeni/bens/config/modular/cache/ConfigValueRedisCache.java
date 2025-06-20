package cn.ibenbeni.bens.config.modular.cache;

import cn.ibenbeni.bens.cache.redis.AbstractRedisCacheOperator;
import cn.ibenbeni.bens.config.api.constants.ConfigConstants;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 参数配置值缓存
 *
 * @author: benben
 * @time: 2025/6/20 下午3:30
 */
public class ConfigValueRedisCache extends AbstractRedisCacheOperator<String> {

    public ConfigValueRedisCache(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return ConfigConstants.CONFIG_VALUE_CACHE_PREFIX;
    }

}
