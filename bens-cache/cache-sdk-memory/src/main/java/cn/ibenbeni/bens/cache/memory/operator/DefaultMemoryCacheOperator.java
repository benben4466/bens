package cn.ibenbeni.bens.cache.memory.operator;

import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.api.constants.CacheConstants;
import cn.ibenbeni.bens.cache.memory.AbstractMemoryCacheOperator;

/**
 * 默认内存缓存的实现，value存放Object类型
 *
 * @author benben
 * @date 2025/5/3  下午3:43
 */
public class DefaultMemoryCacheOperator extends AbstractMemoryCacheOperator<Object> {

    public DefaultMemoryCacheOperator(TimedCache<String, Object> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CacheConstants.DEFAULT_OBJECT_CACHE_PREFIX;
    }

}
