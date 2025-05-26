package cn.ibenbeni.bens.security.api;

/**
 * 拖拽验证码
 *
 * @author benben
 */
public interface DragCaptchaApi {

    /**
     * 验证拖拽验证码
     */
    boolean validateCaptcha(String verKey, Integer verCode);

}
