package cn.ibenbeni.bens.security.starter.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.security.api.constants.CaptchaConstants;
import cn.ibenbeni.bens.security.captcha.cache.CaptchaMemoryCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 安全模块，缓存的依赖
 *
 * @author benben
 * @date 2025/5/25  下午2:40
 */
@Configuration
@ConditionalOnMissingClass("org.springframework.data.redis.connection.RedisConnectionFactory")
public class SecurityMemoryCacheAutoConfiguration {

    /**
     * 验证码相关的缓存，内存缓存
     */
    @Bean("captchaCache")
    public CacheOperatorApi<String> captchaMemoryCache() {
        TimedCache<String, String> timedCache = CacheUtil.newTimedCache(1000 * CaptchaConstants.DRAG_CAPTCHA_IMG_EXP_SECONDS);
        return new CaptchaMemoryCache(timedCache);
    }

}
