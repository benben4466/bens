package cn.ibenbeni.bens.cache.api;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.Map;

import static cn.ibenbeni.bens.cache.api.constants.CacheConstants.CACHE_DELIMITER;

/**
 * 缓存操作的基础接口，可以实现不同种缓存实现
 * <p>泛型为cache的值类class类型</p>
 *
 * @author benben
 */
public interface CacheOperatorApi<T> {

    /**
     * 添加缓存
     *
     * @param key   键
     * @param value 值
     */
    void put(String key, T value);

    /**
     * 添加缓存
     *
     * @param key            键
     * @param value          值
     * @param timeoutSeconds 过期时间，单位秒
     */
    void put(String key, T value, Long timeoutSeconds);

    /**
     * 通过缓存Key获取缓存
     *
     * @param key 键
     * @return 值
     */
    T get(String key);

    /**
     * 删除缓存
     *
     * @param key 键，多个
     */
    void remove(String... key);

    /**
     * 删除缓存
     *
     * @param keys 键，多个
     */
    void remove(Collection<String> keys);

    /**
     * 设置已缓存key的过期时间
     *
     * @param key            键
     * @param timeoutSeconds 过期时间，单位秒
     */
    void expire(String key, Long timeoutSeconds);

    /**
     * 判断某个key值是否存在于缓存
     *
     * @param key 缓存的键
     * @return true-存在，false-不存在
     */
    boolean contains(String key);

    /**
     * 获得缓存的所有key列表（不带common prefix的）
     *
     * @return key列表
     */
    Collection<String> getAllKeys();

    /**
     * 获得缓存的所有值列表
     *
     * @return 值列表
     */
    Collection<T> getAllValues();

    /**
     * 获取所有的key，value
     *
     * @return 键值map
     */
    Map<String, T> getAllKeyValues();

    /**
     * 通用缓存的前缀，用于区分不同业务
     * <p>
     * 如果带了前缀，所有的缓存在添加的时候，key都会带上这个前缀
     *
     * @return 缓存前缀
     */
    String getCommonKeyPrefix();

    /**
     * 获取最终的计算前缀
     * <p>key的组成方式：租户前缀:业务前缀:业务key</p>
     */
    default String getFinalPrefix() {
        return getCommonKeyPrefix() + CACHE_DELIMITER;
    }

    /**
     * 计算最终插入缓存的key值
     * <p>
     * key的组成方式：租户前缀:业务前缀:业务key
     *
     * @param keyParam 用户传递的key参数
     * @return 最终插入缓存的key值
     */
    default String calcKey(String keyParam) {
        if (StrUtil.isEmpty(keyParam)) {
            return getFinalPrefix();
        } else {
            return getFinalPrefix() + keyParam;
        }
    }

    /**
     * 删除缓存key的前缀，返回用户最原始的key
     *
     * @param finalKey 最终存在CacheOperator的key
     * @return 用户最原始的key
     */
    default String removePrefix(String finalKey) {
        if (ObjectUtil.isEmpty(finalKey)) {
            return finalKey;
        }
        // 移除字符串的前缀；移除finalKey中 getFinalPrefix()返回的前缀
        return StrUtil.removePrefix(finalKey, getFinalPrefix());
    }

}
