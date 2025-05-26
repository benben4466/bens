package cn.ibenbeni.bens.auth.cache;

import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.auth.api.constants.LoginCacheConstants;
import cn.ibenbeni.bens.cache.memory.AbstractMemoryCacheOperator;

/**
 * 记录用户登录失败次数的缓存
 * <p>内存</p>
 *
 * @author benben
 * @date 2025/5/22  下午9:14
 */
public class LoginErrorCountMemoryCache extends AbstractMemoryCacheOperator<Integer> {

    public LoginErrorCountMemoryCache(TimedCache<String, Integer> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LoginCacheConstants.LOGIN_ERROR_CACHE_PREFIX;
    }

}
