package cn.ibenbeni.bens.auth.api.pojo.login;

import cn.hutool.core.lang.Dict;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录用户信息
 *
 * @author benben
 * @date 2025/5/3  下午11:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户Token
     */
    private String token;

    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

    /**
     * 登录用户的其他信息
     * <p>不进行持久化</p>
     */
    @JsonIgnore
    private Dict otherInfos;

}
