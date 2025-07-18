package cn.ibenbeni.bens.auth.api.pojo.auth;

import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "账号不能为空", groups = {cancelFreeze.class})
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
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

    // -----------------------------------------------------参数校验分组-------------------------------------------------
    // region 参数校验分组

    /**
     * 取消账号冻结
     */
    public interface cancelFreeze {
    }

    // endregion

}
