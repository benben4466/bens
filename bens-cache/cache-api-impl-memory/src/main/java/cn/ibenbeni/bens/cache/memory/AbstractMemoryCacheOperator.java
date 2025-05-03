package cn.ibenbeni.bens.cache.memory;

import cn.hutool.cache.impl.CacheObj;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 基于内存的缓存封装
 *
 * @author benben
 * @date 2025/5/3  下午3:41
 */
public abstract class AbstractMemoryCacheOperator<T> implements CacheOperatorApi<T> {

    /**
     * 定时缓存工具类(hutool提供)，用于实现带有时间限制的缓存功能
     */
    private final TimedCache<String, T> timedCache;

    public AbstractMemoryCacheOperator(TimedCache<String, T> timedCache) {
        this.timedCache = timedCache;
    }

    @Override
    public void put(String key, T value) {
        timedCache.put(calcKey(key), value);
    }

    @Override
    public void put(String key, T value, Long timeoutSeconds) {
        timedCache.put(calcKey(key), value, timeoutSeconds * 1000);
    }

    @Override
    public T get(String key) {
        // 如果用户在超时前调用了get(key)方法，会重头计算起始时间，false的作用就是不从头算
        return timedCache.get(calcKey(key), false);
    }

    @Override
    public void remove(String... key) {
        for (String itemKey : key) {
            timedCache.remove(calcKey(itemKey));
        }
    }

    @Override
    public void remove(Collection<String> keys) {
        if (ObjectUtil.isEmpty(keys)) {
            return;
        }
        for (String itemKey : keys) {
            timedCache.remove(calcKey(itemKey));
        }
    }

    @Override
    public void expire(String key, Long timeoutSeconds) {
        T value = timedCache.get(calcKey(key), true);
        timedCache.put(calcKey(key), value, timeoutSeconds * 1000);
    }

    @Override
    public boolean contains(String key) {
        return timedCache.containsKey(calcKey(key));
    }

    @Override
    public Collection<String> getAllKeys() {
        Iterator<CacheObj<String, T>> cacheObjIterator = timedCache.cacheObjIterator();
        List<String> keys = CollectionUtil.newArrayList();
        while (cacheObjIterator.hasNext()) {
            String key = cacheObjIterator.next().getKey();
            keys.add(key);
        }
        return keys;
    }

    @Override
    public Collection<T> getAllValues() {
        Iterator<CacheObj<String, T>> cacheObjIterator = timedCache.cacheObjIterator();
        List<T> values = CollectionUtil.newArrayList();
        while (cacheObjIterator.hasNext()) {
            T value = cacheObjIterator.next().getValue();
            values.add(value);
        }
        return values;
    }

    @Override
    public Map<String, T> getAllKeyValues() {
        Collection<String> allKeys = this.getAllKeys();
        Map<String, T> results = MapUtil.newHashMap();
        for (String key : allKeys) {
            results.put(key, this.get(key));
        }
        return results;
    }

}
