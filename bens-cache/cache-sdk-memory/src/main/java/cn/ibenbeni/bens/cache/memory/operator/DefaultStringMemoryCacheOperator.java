package cn.ibenbeni.bens.cache.memory.operator;

import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.api.constants.CacheConstants;
import cn.ibenbeni.bens.cache.memory.AbstractMemoryCacheOperator;

/**
 * 默认内存缓存的实现，value存放String类型
 *
 * @author benben
 * @date 2025/5/3  下午3:46
 */
public class DefaultStringMemoryCacheOperator extends AbstractMemoryCacheOperator<String> {

    public DefaultStringMemoryCacheOperator(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CacheConstants.DEFAULT_STRING_CACHE_PREFIX;
    }

}
