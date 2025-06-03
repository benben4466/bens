package cn.ibenbeni.bens.cache.redis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 记录用户登录失败次数的缓存
 * <p>Redis</p>
 *
 * @author benben
 * @T 缓存的value的类型
 * @date 2025/5/22  下午9:27
 */
public abstract class AbstractRedisCacheOperator<T> implements CacheOperatorApi<T> {

    private RedisTemplate<String, T> redisTemplate;

    public AbstractRedisCacheOperator(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(String key, T value) {
        redisTemplate.opsForValue().set(calcKey(key), value);
    }

    @Override
    public void put(String key, T value, Long timeoutSeconds) {
        redisTemplate.opsForValue().set(calcKey(key), value, timeoutSeconds, TimeUnit.SECONDS);
    }

    @Override
    public T get(String key) {
        return redisTemplate.opsForValue().get(calcKey(key));
    }

    @Override
    public void remove(String... key) {
        for (String itemKey : key) {
            redisTemplate.delete(calcKey(itemKey));
        }
    }

    @Override
    public void remove(Collection<String> keys) {
        if (ObjectUtil.isEmpty(keys)) {
            return;
        }
        for (String itemKey : keys) {
            redisTemplate.delete(calcKey(itemKey));
        }
    }

    @Override
    public void expire(String key, Long timeoutSeconds) {
        redisTemplate.expire(calcKey(key), timeoutSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean contains(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(calcKey(key)));
    }

    @Override
    public Collection<String> getAllKeys() {
        return redisTemplate.keys("*");
    }

    @Override
    public Collection<T> getAllValues() {
        List<T> values = CollectionUtil.newArrayList();
        Collection<String> allKeys = this.getAllKeys();
        if (ObjectUtil.isNotEmpty(allKeys)) {
            for (String key : allKeys) {
                values.add(redisTemplate.opsForValue().get(key));
            }
        }
        return values;
    }

    @Override
    public Map<String, T> getAllKeyValues() {
        Map<String, T> results = MapUtil.newHashMap();
        Collection<String> allKeys = this.getAllKeys();
        if (ObjectUtil.isNotEmpty(allKeys)) {
            for (String key : allKeys) {
                results.put(key, redisTemplate.opsForValue().get(key));
            }
        }
        return results;
    }

}
