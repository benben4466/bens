package cn.ibenbeni.bens.security.starter;

import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.security.api.DragCaptchaApi;
import cn.ibenbeni.bens.security.api.ImageCaptchaApi;
import cn.ibenbeni.bens.security.captcha.DragCaptchaService;
import cn.ibenbeni.bens.security.captcha.ImageCaptchaService;
import cn.ibenbeni.bens.security.starter.cache.SecurityMemoryCacheAutoConfiguration;
import cn.ibenbeni.bens.security.starter.cache.SecurityRedisCacheAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 图形验证码自动配置
 *
 * @author benben
 * @date 2025/5/25  下午2:38
 */
@Configuration
@AutoConfigureAfter({SecurityMemoryCacheAutoConfiguration.class, SecurityRedisCacheAutoConfiguration.class})
public class CaptchaAutoConfiguration {

    @Resource(name = "captchaCache")
    private CacheOperatorApi<String> captchaCache;

    /**
     * 图形验证码
     */
    @Bean
    @ConditionalOnMissingBean(ImageCaptchaApi.class)
    public ImageCaptchaApi captchaApi() {
        // 验证码过期时间 120秒
        return new ImageCaptchaService(captchaCache);
    }

    /**
     * 拖拽验证码
     */
    @Bean
    @ConditionalOnMissingBean(DragCaptchaApi.class)
    public DragCaptchaApi dragCaptchaService() {
        // 验证码过期时间 120秒
        return new DragCaptchaService(captchaCache);
    }

}
