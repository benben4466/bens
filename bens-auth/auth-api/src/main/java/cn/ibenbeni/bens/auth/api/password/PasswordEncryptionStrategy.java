package cn.ibenbeni.bens.auth.api.password;

import cn.ibenbeni.bens.auth.api.password.pojo.password.SaltedEncryptResult;
import cn.ibenbeni.bens.rule.strategy.CustomStrategy;

/**
 * 密码加密策略
 * <p>需要一个条件类将密码加密模式引导</p>
 *
 * @author benben
 * @date 2025/5/3  下午4:04
 */
public interface PasswordEncryptionStrategy extends CustomStrategy {

    /**
     * 加密密码
     * <p>原始密码+盐</p>
     *
     * @param originPassword 原始密码
     * @return 加密后的密码及盐
     */
    SaltedEncryptResult encryptWithSalt(String originPassword);

    /**
     * 校验密码，通过密码 + 盐的方式
     *
     * @param encryptBefore 未加密密码
     * @param passwordSalt  密码盐
     * @param encryptAfter  已加密密码
     * @return 是否匹配
     */
    Boolean checkPasswordWithSalt(String encryptBefore, String passwordSalt, String encryptAfter);

}
