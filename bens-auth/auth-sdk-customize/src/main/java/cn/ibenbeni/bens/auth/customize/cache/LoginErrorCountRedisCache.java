package cn.ibenbeni.bens.auth.customize.cache;

import cn.ibenbeni.bens.auth.api.constants.LoginCacheConstants;
import cn.ibenbeni.bens.cache.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 登录错误次数缓存
 * <p>key=用户账号；value=登录失败次数</p>
 * <p>Redis缓存</p>
 *
 * @author benben
 * @date 2025/5/22  下午9:22
 */
public class LoginErrorCountRedisCache extends AbstractRedisCacheOperator<Integer> {

    public LoginErrorCountRedisCache(RedisTemplate<String, Integer> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LoginCacheConstants.LOGIN_ERROR_CACHE_PREFIX;
    }

}
