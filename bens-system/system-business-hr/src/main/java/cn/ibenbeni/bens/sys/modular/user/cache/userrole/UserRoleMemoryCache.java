package cn.ibenbeni.bens.sys.modular.user.cache.userrole;

import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.memory.AbstractMemoryCacheOperator;
import cn.ibenbeni.bens.sys.modular.user.constants.UserConstants;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserRole;

import java.util.List;

/**
 * 用户绑定角色的缓存
 *
 * @author: benben
 * @time: 2025/6/2 下午3:55
 */
public class UserRoleMemoryCache extends AbstractMemoryCacheOperator<List<SysUserRole>> {

    public UserRoleMemoryCache(TimedCache<String, List<SysUserRole>> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return UserConstants.USER_ROLE_CACHE_PREFIX;
    }

}
