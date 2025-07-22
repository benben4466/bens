package cn.ibenbeni.bens.auth.customize.password;

import cn.ibenbeni.bens.auth.api.password.PasswordEncryptionStrategy;
import cn.ibenbeni.bens.auth.api.pojo.password.SaltedEncryptResult;

/**
 * MD5加密策略【密码加密】
 */
public class MD5PasswordEncryptionStrategy implements PasswordEncryptionStrategy {

    @Override
    public SaltedEncryptResult encryptWithSalt(String originPassword) {
        return null;
    }

    @Override
    public Boolean checkPasswordWithSalt(String encryptBefore, String passwordSalt, String encryptAfter) {
        return null;
    }

}
