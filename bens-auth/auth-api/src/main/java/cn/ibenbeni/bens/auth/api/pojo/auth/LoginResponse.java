package cn.ibenbeni.bens.auth.api.pojo.auth;

import lombok.Data;

/**
 * @author benben
 * @date 2025/5/20  下午3:23
 */
@Data
public class LoginResponse {

    /**
     * 登录人id
     */
    private Long userId;

    /**
     * 登录人的token
     */
    private String token;

    public LoginResponse(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

}
