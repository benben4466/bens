package cn.ibenbeni.bens.sys.modular.user.cache.userrole;

import cn.ibenbeni.bens.cache.redis.AbstractRedisCacheOperator;
import cn.ibenbeni.bens.sys.modular.user.constants.UserConstants;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserRole;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 用户绑定角色的缓存
 *
 * @author: benben
 * @time: 2025/6/2 下午3:53
 */
public class UserRoleRedisCache extends AbstractRedisCacheOperator<List<SysUserRole>> {

    public UserRoleRedisCache(RedisTemplate<String, List<SysUserRole>> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return UserConstants.USER_ROLE_CACHE_PREFIX;
    }

}
