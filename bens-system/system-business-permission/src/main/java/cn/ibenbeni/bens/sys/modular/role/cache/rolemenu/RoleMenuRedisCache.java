package cn.ibenbeni.bens.sys.modular.role.cache.rolemenu;

import cn.ibenbeni.bens.cache.redis.AbstractRedisCacheOperator;
import cn.ibenbeni.bens.sys.modular.role.constants.RoleConstants;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 角色绑定菜单的缓存
 *
 * @author: benben
 * @time: 2025/6/3 下午9:31
 */
public class RoleMenuRedisCache extends AbstractRedisCacheOperator<List<Long>> {

    public RoleMenuRedisCache(RedisTemplate<String, List<Long>> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return RoleConstants.ROLE_MENU_CACHE_PREFIX;
    }

}
