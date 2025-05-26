package cn.ibenbeni.bens.auth.session.cache.loginuser;

import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.auth.api.constants.AuthConstants;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.cache.memory.AbstractMemoryCacheOperator;

/**
 * 基于内存的登录用户缓存
 *
 * @author benben
 * @date 2025/5/25  下午2:25
 */
public class MemoryLoginUserCache extends AbstractMemoryCacheOperator<LoginUser> {

    public MemoryLoginUserCache(TimedCache<String, LoginUser> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return AuthConstants.LOGGED_TOKEN_PREFIX;
    }

}
