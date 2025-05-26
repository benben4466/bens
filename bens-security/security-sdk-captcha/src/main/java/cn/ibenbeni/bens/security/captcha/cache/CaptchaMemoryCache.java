package cn.ibenbeni.bens.security.captcha.cache;

import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.memory.AbstractMemoryCacheOperator;
import cn.ibenbeni.bens.security.api.constants.CaptchaConstants;

/**
 * 图形验证码缓存
 *
 * @author benben
 * @date 2025/5/25  下午2:43
 */
public class CaptchaMemoryCache extends AbstractMemoryCacheOperator<String> {

    public CaptchaMemoryCache(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CaptchaConstants.CAPTCHA_CACHE_KEY_PREFIX;
    }

}
