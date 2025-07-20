package cn.ibenbeni.bens.sys.modular.user.cache.userrole;

import cn.ibenbeni.bens.cache.redis.AbstractRedisCacheOperator;
import cn.ibenbeni.bens.sys.modular.user.constants.UserConstants;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserRoleDO;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 用户绑定角色的缓存
 *
 * @author: benben
 * @time: 2025/6/2 下午3:53
 */
public class UserRoleRedisCache extends AbstractRedisCacheOperator<List<SysUserRoleDO>> {

    public UserRoleRedisCache(RedisTemplate<String, List<SysUserRoleDO>> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return UserConstants.USER_ROLE_CACHE_PREFIX;
    }

}
