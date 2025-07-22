package cn.ibenbeni.bens.auth.customize.session.cache.logintoken;

import cn.ibenbeni.bens.auth.api.constants.AuthConstants;
import cn.ibenbeni.bens.cache.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 基于Redis缓存用户Token
 */
public class RedisLoginTokenCache extends AbstractRedisCacheOperator<String> {

    public RedisLoginTokenCache(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return AuthConstants.LOGGED_TOKEN_PREFIX;
    }

}
