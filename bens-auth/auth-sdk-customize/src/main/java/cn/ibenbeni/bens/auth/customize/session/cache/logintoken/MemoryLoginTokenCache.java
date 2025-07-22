package cn.ibenbeni.bens.auth.customize.session.cache.logintoken;

import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.auth.api.constants.AuthConstants;
import cn.ibenbeni.bens.cache.memory.AbstractMemoryCacheOperator;

/**
 * 基于内存缓存用户Token
 */
public class MemoryLoginTokenCache extends AbstractMemoryCacheOperator<String> {

    public MemoryLoginTokenCache(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return AuthConstants.LOGGED_TOKEN_PREFIX;
    }

}
