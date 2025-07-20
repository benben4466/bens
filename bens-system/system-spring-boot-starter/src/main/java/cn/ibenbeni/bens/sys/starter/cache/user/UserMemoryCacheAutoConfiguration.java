package cn.ibenbeni.bens.sys.starter.cache.user;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.sys.api.constants.SysConstants;
import cn.ibenbeni.bens.sys.modular.user.cache.userrole.UserRoleMemoryCache;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserRoleDO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 用户相关所有缓存
 *
 * @author: benben
 * @time: 2025/6/2 下午3:47
 */
@Configuration
@ConditionalOnMissingClass("org.springframework.data.redis.connection.RedisConnectionFactory")
public class UserMemoryCacheAutoConfiguration {

    /**
     * 用户绑定角色的缓存
     */
    @Bean
    public CacheOperatorApi<List<SysUserRoleDO>> userRoleCache() {
        // 1小时过期
        TimedCache<String, List<SysUserRoleDO>> cache = CacheUtil.newTimedCache(1000 * SysConstants.DEFAULT_SYS_CACHE_TIMEOUT_SECONDS);
        return new UserRoleMemoryCache(cache);
    }

}
