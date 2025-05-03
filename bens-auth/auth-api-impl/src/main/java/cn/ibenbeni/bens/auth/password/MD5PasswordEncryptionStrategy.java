package cn.ibenbeni.bens.auth.password;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.ibenbeni.bens.auth.api.constants.PasswordEncryptionConstants;
import cn.ibenbeni.bens.auth.api.password.PasswordEncryptionStrategy;
import cn.ibenbeni.bens.auth.api.password.pojo.password.SaltedEncryptResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * MD5加密策略【密码加密】
 *
 * @author benben
 * @date 2025/5/3  下午4:07
 */
@Component
@ConditionalOnProperty(name = "bens.security.password", havingValue = PasswordEncryptionConstants.MD5)
public class MD5PasswordEncryptionStrategy implements PasswordEncryptionStrategy {

    @Override
    public SaltedEncryptResult encryptWithSalt(String originPassword) {
        String salt = RandomUtil.randomString(8);
        String encryptAfter = SecureUtil.md5(originPassword + salt);

        SaltedEncryptResult saltedEncryptResult = new SaltedEncryptResult();
        saltedEncryptResult.setPasswordSalt(salt);
        saltedEncryptResult.setEncryptPassword(encryptAfter);
        return saltedEncryptResult;
    }

    @Override
    public Boolean checkPasswordWithSalt(String encryptBefore, String passwordSalt, String encryptAfter) {
        return SecureUtil.md5(encryptBefore + passwordSalt).equals(encryptAfter);
    }

}
