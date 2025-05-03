package cn.ibenbeni.bens.auth.api.password.pojo.password;

import lombok.Data;

/**
 * 密码加密结果
 *
 * @author benben
 * @date 2025/5/3  下午4:06
 */
@Data
public class SaltedEncryptResult {

    /**
     * 加密后的密码
     */
    private String encryptPassword;

    /**
     * 密码盐
     */
    private String passwordSalt;

}
