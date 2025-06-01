package cn.ibenbeni.bens.sys.modular.menu.cache.menucode;

import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.memory.AbstractMemoryCacheOperator;
import cn.ibenbeni.bens.sys.modular.menu.constants.MenuConstants;

/**
 * 菜单编码的缓存 或 菜单功能编码缓存
 *
 * @author: benben
 * @time: 2025/6/1 下午5:00
 */
public class MenuCodeMemoryCache extends AbstractMemoryCacheOperator<String> {

    public MenuCodeMemoryCache(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return MenuConstants.MENU_CODE_CACHE_PREFIX;
    }

}
