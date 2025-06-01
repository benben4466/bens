package cn.ibenbeni.bens.sys.starter.cache.menu;

import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.cache.redis.util.CreateRedisTemplateUtil;
import cn.ibenbeni.bens.sys.modular.menu.cache.menucode.MenuCodeRedisCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 菜单的缓存
 *
 * @author: benben
 * @time: 2025/6/1 下午5:05
 */
@Configuration
@ConditionalOnClass(name = "org.springframework.data.redis.connection.RedisConnectionFactory")
public class MenuRedisCacheAutoConfiguration {

    /**
     * 菜单编码的缓存
     */
    @Bean
    public CacheOperatorApi<String> menuCodeCache(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = CreateRedisTemplateUtil.createString(redisConnectionFactory);
        return new MenuCodeRedisCache(redisTemplate);
    }

}
