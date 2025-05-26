package cn.ibenbeni.bens.auth.api.pojo.auth;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录的请求参数
 *
 * @author benben
 * @date 2025/5/20  下午3:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequest extends BaseRequest {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 记录会话，7天免登录
     */
    private Boolean rememberMe = false;

    /**
     * 验证码图形对应的缓存key
     */
    private String verKey;

    /**
     * 用户输入的验证码的值
     */
    private String verCode;

}
