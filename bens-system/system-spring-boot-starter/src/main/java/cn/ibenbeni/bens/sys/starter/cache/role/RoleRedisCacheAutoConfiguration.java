package cn.ibenbeni.bens.sys.starter.cache.role;

import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.cache.redis.util.CreateRedisTemplateUtil;
import cn.ibenbeni.bens.sys.modular.role.cache.RoleMenuRedisCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 角色缓存
 *
 * @author: benben
 * @time: 2025/6/3 下午9:26
 */
@Configuration
@ConditionalOnClass(name = "org.springframework.data.redis.connection.RedisConnectionFactory")
public class RoleRedisCacheAutoConfiguration {

    /**
     * 角色绑定菜单的缓存
     */
    @Bean
    public CacheOperatorApi<List<Long>> roleMenuCache(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, List<Long>> redisTemplate = CreateRedisTemplateUtil.createObject(redisConnectionFactory);
        return new RoleMenuRedisCache(redisTemplate);
    }

}
