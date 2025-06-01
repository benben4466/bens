package cn.ibenbeni.bens.sys.modular.menu.cache.menucode;

import cn.ibenbeni.bens.cache.redis.AbstractRedisCacheOperator;
import cn.ibenbeni.bens.sys.modular.menu.constants.MenuConstants;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 菜单编码的缓存 或 菜单功能编码缓存
 *
 * @author: benben
 * @time: 2025/6/1 下午5:03
 */
public class MenuCodeRedisCache extends AbstractRedisCacheOperator<String> {

    public MenuCodeRedisCache(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return MenuConstants.MENU_CODE_CACHE_PREFIX;
    }

}
