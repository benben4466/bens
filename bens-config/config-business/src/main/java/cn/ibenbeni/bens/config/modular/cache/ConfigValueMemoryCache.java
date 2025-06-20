package cn.ibenbeni.bens.config.modular.cache;

import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.memory.AbstractMemoryCacheOperator;
import cn.ibenbeni.bens.config.api.constants.ConfigConstants;

/**
 * 参数配置值缓存
 *
 * @author: benben
 * @time: 2025/6/20 下午3:28
 */
public class ConfigValueMemoryCache extends AbstractMemoryCacheOperator<String> {

    public ConfigValueMemoryCache(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return ConfigConstants.CONFIG_VALUE_CACHE_PREFIX;
    }

}
