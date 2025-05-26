package cn.ibenbeni.bens.cache.redis.operator;

import cn.ibenbeni.bens.cache.api.constants.CacheConstants;
import cn.ibenbeni.bens.cache.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 默认Redis缓存的实现，value存放Object类型
 *
 * @author benben
 * @date 2025/5/24  下午11:23
 */
public class DefaultRedisCacheOperator extends AbstractRedisCacheOperator<Object> {

    public DefaultRedisCacheOperator(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CacheConstants.DEFAULT_OBJECT_CACHE_PREFIX;
    }

}
