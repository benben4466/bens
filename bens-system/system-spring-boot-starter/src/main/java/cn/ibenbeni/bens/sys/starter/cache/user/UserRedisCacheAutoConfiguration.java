package cn.ibenbeni.bens.sys.starter.cache.user;

import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.cache.redis.util.CreateRedisTemplateUtil;
import cn.ibenbeni.bens.sys.modular.user.cache.userrole.UserRoleRedisCache;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserRoleDO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 用户相关所有缓存
 *
 * @author: benben
 * @time: 2025/6/2 下午3:48
 */
@Configuration
@ConditionalOnClass(name = "org.springframework.data.redis.connection.RedisConnectionFactory")
public class UserRedisCacheAutoConfiguration {

    /**
     * 用户绑定角色的缓存
     */
    @Bean
    public CacheOperatorApi<List<SysUserRoleDO>> userRoleCache(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, List<SysUserRoleDO>> redisTemplate = CreateRedisTemplateUtil.createObject(redisConnectionFactory);
        return new UserRoleRedisCache(redisTemplate);
    }

}
