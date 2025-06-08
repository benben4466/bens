package cn.ibenbeni.bens.sys.starter.cache.role;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.sys.api.constants.SysConstants;
import cn.ibenbeni.bens.sys.modular.role.cache.rolemenu.RoleMenuMemoryCache;
import cn.ibenbeni.bens.sys.modular.role.cache.roleoptions.RoleMenuOptionsMemoryCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 角色缓存
 *
 * @author: benben
 * @time: 2025/6/3 下午9:25
 */
@Configuration
@ConditionalOnMissingClass("org.springframework.data.redis.connection.RedisConnectionFactory")
public class RoleMemoryCacheAutoConfiguration {

    /**
     * 角色绑定菜单的缓存
     */
    @Bean
    public CacheOperatorApi<List<Long>> roleMenuCache() {
        TimedCache<String, List<Long>> timedCache = CacheUtil.newTimedCache(1000 * SysConstants.DEFAULT_SYS_CACHE_TIMEOUT_SECONDS);
        return new RoleMenuMemoryCache(timedCache);
    }

    /**
     * 角色绑定菜单功能缓存
     */
    @Bean
    public CacheOperatorApi<List<Long>> roleMenuOptionsCache() {
        TimedCache<String, List<Long>> timedCache = CacheUtil.newTimedCache(1000 * SysConstants.DEFAULT_SYS_CACHE_TIMEOUT_SECONDS);
        return new RoleMenuOptionsMemoryCache(timedCache);
    }

}
