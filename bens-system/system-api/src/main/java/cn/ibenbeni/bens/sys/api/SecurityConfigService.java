package cn.ibenbeni.bens.sys.api;

import cn.ibenbeni.bens.sys.api.pojo.security.SecurityConfig;

/**
 * 系统安全配置的业务
 *
 * @author benben
 */
public interface SecurityConfigService {

    /**
     * 获取系统安全配置
     */
    SecurityConfig getSecurityConfig();

    /**
     * 校验密码是否符合当前配置的安全规则，如果不符合规则，直接抛出异常
     *
     * @param updatePasswordFlag 是否是修改密码的标识
     * @param password           密码
     */
    void validatePasswordSecurityRule(boolean updatePasswordFlag, String password);

    /**
     * 记录用户密码修改的日志
     *
     * @param userId 用户ID
     * @param md5    加密密码
     * @param salt   盐
     */
    void recordPasswordEditLog(Long userId, String md5, String salt);

}
