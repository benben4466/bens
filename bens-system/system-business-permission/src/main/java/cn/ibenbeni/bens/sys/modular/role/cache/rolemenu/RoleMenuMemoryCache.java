package cn.ibenbeni.bens.sys.modular.role.cache.rolemenu;

import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.memory.AbstractMemoryCacheOperator;
import cn.ibenbeni.bens.sys.modular.role.constants.RoleConstants;

import java.util.List;

/**
 * 角色绑定菜单的缓存
 *
 * @author: benben
 * @time: 2025/6/3 下午9:29
 */
public class RoleMenuMemoryCache extends AbstractMemoryCacheOperator<List<Long>> {

    public RoleMenuMemoryCache(TimedCache<String, List<Long>> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return RoleConstants.ROLE_MENU_CACHE_PREFIX;
    }

}
