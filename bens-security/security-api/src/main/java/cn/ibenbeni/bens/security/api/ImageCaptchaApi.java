package cn.ibenbeni.bens.security.api;

/**
 * 图形验证码Api
 * <p>开启用户登录图形验证码后获取图形验证码</p>
 *
 * @author benben
 */
public interface ImageCaptchaApi {

    /**
     * 校验图形验证码
     *
     * @param verKey  缓存key值
     * @param verCode 验证码
     * @return true-验证码正确，false-验证码错误
     */
    boolean validateCaptcha(String verKey, String verCode);

}
