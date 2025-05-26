package cn.ibenbeni.bens.security.captcha.cache;

import cn.ibenbeni.bens.cache.redis.AbstractRedisCacheOperator;
import cn.ibenbeni.bens.security.api.constants.CaptchaConstants;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 图形验证码缓存
 *
 * @author benben
 * @date 2025/5/25  下午2:46
 */
public class CaptchaRedisCache extends AbstractRedisCacheOperator<String> {

    public CaptchaRedisCache(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CaptchaConstants.CAPTCHA_CACHE_KEY_PREFIX;
    }

}
