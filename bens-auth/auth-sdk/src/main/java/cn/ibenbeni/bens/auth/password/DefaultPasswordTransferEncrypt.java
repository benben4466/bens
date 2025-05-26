package cn.ibenbeni.bens.auth.password;

import cn.ibenbeni.bens.auth.api.password.PasswordTransferEncryptApi;

/**
 * 默认密码转换加密实现
 *
 * @author benben
 * @date 2025/5/20  下午4:46
 */
public class DefaultPasswordTransferEncrypt implements PasswordTransferEncryptApi {

    @Override
    public String encrypt(String originPassword) {
        // TODO 待实现
        return originPassword;
    }

    @Override
    public String decrypt(String encryptedPassword) {
        return encryptedPassword;
    }

}
