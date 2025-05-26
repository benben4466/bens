package cn.ibenbeni.bens.cache.redis.operator;

import cn.ibenbeni.bens.cache.api.constants.CacheConstants;
import cn.ibenbeni.bens.cache.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 默认Redis缓存的实现，value存放String类型
 *
 * @author benben
 * @date 2025/5/24  下午11:25
 */
public class DefaultStringRedisCacheOperator extends AbstractRedisCacheOperator<String> {

    public DefaultStringRedisCacheOperator(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CacheConstants.DEFAULT_STRING_CACHE_PREFIX;
    }

}
