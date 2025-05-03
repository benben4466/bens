package cn.ibenbeni.bens.sys.modular.security.service.impl;

import cn.ibenbeni.bens.auth.api.password.PasswordEncryptionStrategy;
import cn.ibenbeni.bens.sys.api.SecurityConfigService;
import cn.ibenbeni.bens.sys.api.exception.SysException;
import cn.ibenbeni.bens.sys.api.exception.enums.SecurityStrategyExceptionEnum;
import cn.ibenbeni.bens.sys.api.pojo.security.SecurityConfig;
import cn.ibenbeni.bens.sys.modular.security.entity.SysUserPasswordRecord;
import cn.ibenbeni.bens.sys.modular.security.service.SysUserPasswordRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 系统安全配置的业务
 *
 * @author benben
 * @date 2025/4/19  下午1:50
 */
@Service
public class SecurityConfigServiceImpl implements SecurityConfigService {

    @Resource
    private PasswordEncryptionStrategy passwordEncryptionStrategy;

    @Resource
    private SysUserPasswordRecordService sysUserPasswordRecordService;

    @Override
    public SecurityConfig getSecurityConfig() {
        SecurityConfig securityConfig = new SecurityConfig();
        securityConfig.setMaxErrorLoginCount(5);
        securityConfig.setMinPasswordLength(6);

        securityConfig.setPasswordMinSpecialSymbolCount(0);
        securityConfig.setGetPasswordMinUpperCaseCount(0);
        securityConfig.setPasswordMinLowerCaseCount(0);
        securityConfig.setPasswordMinNumberCount(0);

        securityConfig.setPasswordMinUpdateDays(180);
        securityConfig.setPasswordMinCantRepeatTimes(0);
        return securityConfig;
    }

    @Override
    public void validatePasswordSecurityRule(boolean updatePasswordFlag, String password) {
        // 获取现在密码规则
        SecurityConfig securityConfig = this.getSecurityConfig();

        // 1. 校验密码长度是否符合规则
        if (password.length() < securityConfig.getMinPasswordLength()) {
            throw new SysException(SecurityStrategyExceptionEnum.PASSWORD_LENGTH);
        }

        // 2. 校验密码中特殊字符的数量
        int specialSymbolCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            // 判断一个字符是否是字母或数字
            if (!Character.isLetterOrDigit(c)) {
                specialSymbolCount++;
            }
        }
        if (specialSymbolCount < securityConfig.getPasswordMinSpecialSymbolCount()) {
            throw new SysException(SecurityStrategyExceptionEnum.SPECIAL_SYMBOL);
        }

        // 3. 校验密码中大写字母的数量
        int upperCaseCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                upperCaseCount++;
            }
        }
        if (upperCaseCount < securityConfig.getGetPasswordMinUpperCaseCount()) {
            throw new SysException(SecurityStrategyExceptionEnum.UPPER_CASE);
        }

        // 4. 校验密码中小写字母的数量
        int lowerCaseCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isLowerCase(c)) {
                lowerCaseCount++;
            }
        }
        if (lowerCaseCount < securityConfig.getPasswordMinLowerCaseCount()) {
            throw new SysException(SecurityStrategyExceptionEnum.LOWER_CASE);
        }

        // 5. 校验密码中数字的数量
        int numberCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                numberCount++;
            }
        }
        if (numberCount < securityConfig.getPasswordMinNumberCount()) {
            throw new SysException(SecurityStrategyExceptionEnum.NUMBER_SYMBOL);
        }

        // 6. 如果是修改密码，则校验密码是否和最近几次的密码相同
        Integer passwordMinCantRepeatTimes = securityConfig.getPasswordMinCantRepeatTimes();
        // 如果为0则不用校验
        if (passwordMinCantRepeatTimes == null || passwordMinCantRepeatTimes.equals(0)) {
            return;
        }
        // 密码修改校验
        List<SysUserPasswordRecord> recentRecords = sysUserPasswordRecordService.getRecentRecords(-1L, passwordMinCantRepeatTimes);
        for (SysUserPasswordRecord recentRecord : recentRecords) {
            Boolean resultTrue = passwordEncryptionStrategy.checkPasswordWithSalt(password, recentRecord.getHistoryPasswordSalt(), recentRecord.getHistoryPassword());
            if (resultTrue) {
                throw new SysException(SecurityStrategyExceptionEnum.PASSWORD_REPEAT);
            }
        }
    }

    @Override
    public void recordPasswordEditLog(Long userId, String md5, String salt) {
        SysUserPasswordRecord sysUserPasswordRecord = new SysUserPasswordRecord();
        sysUserPasswordRecord.setUserId(userId);
        sysUserPasswordRecord.setHistoryPassword(md5);
        sysUserPasswordRecord.setHistoryPasswordSalt(md5);
        sysUserPasswordRecord.setUpdatePasswordTime(new Date());
        sysUserPasswordRecordService.add(sysUserPasswordRecord);
    }

}
