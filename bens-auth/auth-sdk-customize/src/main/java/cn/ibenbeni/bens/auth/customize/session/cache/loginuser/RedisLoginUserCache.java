package cn.ibenbeni.bens.auth.customize.session.cache.loginuser;

import cn.ibenbeni.bens.auth.api.constants.AuthConstants;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.cache.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 基于Redis的登录用户缓存
 *
 * @author benben
 * @date 2025/5/25  下午2:26
 */
public class RedisLoginUserCache extends AbstractRedisCacheOperator<LoginUser> {

    public RedisLoginUserCache(RedisTemplate<String, LoginUser> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return AuthConstants.LOGGED_USERID_PREFIX;
    }

}
