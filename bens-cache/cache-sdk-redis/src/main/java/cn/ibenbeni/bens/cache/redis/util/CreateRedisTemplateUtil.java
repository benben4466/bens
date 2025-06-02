package cn.ibenbeni.bens.cache.redis.util;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 创建RedisTemplate工具类
 *
 * @author benben
 * @date 2025/5/25  上午8:54
 */
public final class CreateRedisTemplateUtil {

    /**
     * 创建 RedisTemplate
     */
    public static <T> RedisTemplate<String, T> createObject(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        // 设置 RedisConnectionFactory
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 初始化 RedisTemplate
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 创建 RedisTemplate
     */
    public static RedisTemplate<String, String> createString(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        // 设置 RedisConnectionFactory
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 初始化 RedisTemplate
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
