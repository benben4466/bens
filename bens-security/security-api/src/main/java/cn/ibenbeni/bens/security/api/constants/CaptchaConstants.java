package cn.ibenbeni.bens.security.api.constants;

/**
 * 图形验证码模块的常量
 *
 * @author benben
 */
public interface CaptchaConstants {

    /**
     * 验证码 缓存前缀标识
     */
    String CAPTCHA_CACHE_KEY_PREFIX = "CAPTCHA_KEY";

    /**
     * 拖拽验证码缓存过期时间
     */
    Long DRAG_CAPTCHA_IMG_EXP_SECONDS = 120L;

}
