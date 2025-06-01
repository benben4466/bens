package cn.ibenbeni.bens.sys.starter.cache.menu;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.sys.api.constants.SysConstants;
import cn.ibenbeni.bens.sys.modular.menu.cache.menucode.MenuCodeMemoryCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 菜单的缓存
 *
 * @author: benben
 * @time: 2025/6/1 下午4:58
 */
@Configuration
@ConditionalOnMissingClass("org.springframework.data.redis.connection.RedisConnectionFactory")
public class MenuCacheAutoConfiguration {

    /**
     * 菜单编码的缓存
     */
    @Bean
    public CacheOperatorApi<String> menuCodeCache() {
        // 1小时过期
        TimedCache<String, String> themeCache = CacheUtil.newTimedCache(1000 * SysConstants.DEFAULT_SYS_CACHE_TIMEOUT_SECONDS);
        return new MenuCodeMemoryCache(themeCache);
    }

}
