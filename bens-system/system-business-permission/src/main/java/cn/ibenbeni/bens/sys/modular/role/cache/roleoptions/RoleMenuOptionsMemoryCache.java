package cn.ibenbeni.bens.sys.modular.role.cache.roleoptions;

import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.memory.AbstractMemoryCacheOperator;
import cn.ibenbeni.bens.sys.modular.role.constants.RoleConstants;

import java.util.List;

/**
 * 角色绑定菜单的缓存
 *
 * @author: benben
 * @time: 2025/6/8 下午4:39
 */
public class RoleMenuOptionsMemoryCache extends AbstractMemoryCacheOperator<List<Long>> {

    public RoleMenuOptionsMemoryCache(TimedCache<String, List<Long>> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return RoleConstants.ROLE_MENU_OPTIONS_CACHE_PREFIX;
    }

}
