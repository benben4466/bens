package cn.ibenbeni.bens.sys.modular.role.cache.roleoptions;

import cn.ibenbeni.bens.cache.redis.AbstractRedisCacheOperator;
import cn.ibenbeni.bens.sys.modular.role.constants.RoleConstants;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 角色绑定菜单功能
 *
 * @author: benben
 * @time: 2025/6/8 下午4:40
 */
public class RoleMenuOptionsRedisCache extends AbstractRedisCacheOperator<List<Long>> {

    public RoleMenuOptionsRedisCache(RedisTemplate<String, List<Long>> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return RoleConstants.ROLE_MENU_OPTIONS_CACHE_PREFIX;
    }

}
