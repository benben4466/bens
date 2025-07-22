package cn.ibenbeni.bens.cache.api.constants;

/**
 * 缓存模块的常量
 *
 * @author benben
 */
public interface CacheConstants {

    /**
     * 缓存的分割符号
     */
    String CACHE_DELIMITER = ":";

    /**
     * 默认缓存的过期时间，10分钟
     */
    Long DEFAULT_CACHE_TIMEOUT = 1000L * 60 * 10;

    /**
     * 默认object对象缓存的缓存前缀
     */
    String DEFAULT_OBJECT_CACHE_PREFIX = "DEFAULT:OBJECTS:";

    /**
     * 默认String对象缓存的缓存前缀
     */
    String DEFAULT_STRING_CACHE_PREFIX = "DEFAULT:STRINGS:";

    /**
     * 给hutool缓存用的无限过期时间
     */
    Long NONE_EXPIRED_TIME = 1000L * 3600 * 24 * 999;

}
