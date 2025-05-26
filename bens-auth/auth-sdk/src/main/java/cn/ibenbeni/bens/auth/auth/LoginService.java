package cn.ibenbeni.bens.auth.auth;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.auth.api.SessionManagerApi;
import cn.ibenbeni.bens.auth.api.context.LoginUserHolder;
import cn.ibenbeni.bens.auth.api.exception.AuthException;
import cn.ibenbeni.bens.auth.api.exception.enums.AuthExceptionEnum;
import cn.ibenbeni.bens.auth.api.password.PasswordEncryptionStrategy;
import cn.ibenbeni.bens.auth.api.password.PasswordTransferEncryptApi;
import cn.ibenbeni.bens.auth.api.pojo.auth.LoginRequest;
import cn.ibenbeni.bens.auth.api.pojo.auth.LoginResponse;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.log.api.LoginLogServiceApi;
import cn.ibenbeni.bens.rule.util.HttpServletUtil;
import cn.ibenbeni.bens.security.api.DragCaptchaApi;
import cn.ibenbeni.bens.security.api.ImageCaptchaApi;
import cn.ibenbeni.bens.sys.api.SysUserServiceApi;
import cn.ibenbeni.bens.sys.api.enums.user.UserStatusEnum;
import cn.ibenbeni.bens.sys.api.pojo.user.UserValidateDTO;
import cn.ibenbeni.bens.validator.api.exception.enums.ValidatorExceptionEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 登录相关的逻辑封装
 *
 * @author benben
 * @date 2025/5/20  下午3:22
 */
@Service
public class LoginService {

    @Resource
    private SysUserServiceApi sysUserServiceApi;

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private PasswordTransferEncryptApi passwordTransferEncryptApi;

    @Resource
    private PasswordEncryptionStrategy passwordEncryptionStrategy;

    @Resource
    private ImageCaptchaApi captchaApi;

    @Resource
    private DragCaptchaApi dragCaptchaApi;

    @Resource
    private LoginLogServiceApi loginLogServiceApi;

    @Resource(name = "loginErrorCountCacheApi")
    private CacheOperatorApi<Integer> loginErrorCountCacheApi;

    /**
     * 登录的真正业务逻辑
     *
     * @param loginRequest     登陆参数
     * @param validatePassword 是否校验密码，true-校验密码，false-不会校验密码
     */
    public LoginResponse loginAction(LoginRequest loginRequest, Boolean validatePassword) {
        // TODO 校验当前系统是否初始化资源完成，如果资源还没有初始化，提示用户请等一下再登录

        // 若已登陆，则直接返回
//        if (StpUtil.isLogin()) {
//            return null;
//        }

        // 1.参数为空校验
        validateEmptyParams(loginRequest, validatePassword);
        // 1.2 判断账号是否密码重试次数过多被冻结
        Integer loginErrorCount = validatePasswordRetryTimes(loginRequest);

        // 2. 如果开启了验证码校验，则验证当前请求的验证码是否正确
        if (validatePassword) {
            // TODO 暂时取消
            // validateCaptcha(loginRequest);
        }

        // 3. 解密密码的密文，需要sys_config相关配置打开(用户前后端加密传输)
        decryptRequestPassword(loginRequest);

        // 4. 通过租户编码获取租户id，如果租户参数没传，则默认填充根租户的id

        // 5. 获取用户密码的加密值和用户的状态
        UserValidateDTO userValidateInfo = sysUserServiceApi.getUserLoginValidateDTO(loginRequest.getAccount());

        // 6. 校验用户是否异常（不是正常状态）
        if (!UserStatusEnum.ENABLE.getCode().equals(userValidateInfo.getUserStatus())) {
            throw new AuthException(AuthExceptionEnum.USER_STATUS_ERROR, UserStatusEnum.getCodeMessage(userValidateInfo.getUserStatus()));
        }

        // 7. 校验用户密码是否正确
        validateUserPassword(validatePassword, loginErrorCount, loginRequest, userValidateInfo);

        // 8. 生成用户的token
        StpUtil.login(userValidateInfo.getAccount());
        String userLoginToken = StpUtil.getTokenValue();

        // 9. 创建loginUser对象
        LoginUser loginUser = new LoginUser(userValidateInfo.getUserId(), loginRequest.getAccount(), userLoginToken);
        // 9.1 记录用户登录时间和ip
        String ip = HttpServletUtil.getRequestClientIp(HttpServletUtil.getRequest());
        loginUser.setLoginIp(ip);
        loginUser.setLoginTime(new Date());
        loginUser.setCurrentOrgId(111L);

        // intern返回字符串引用
        synchronized (loginRequest.getAccount().intern()) {
            // 10. 缓存用户信息，创建会话
            sessionManagerApi.createSession(userLoginToken, loginUser);

            // 11. 如果开启了单账号单端在线，则踢掉已经上线的该用户
            // TODO config模块完善后补充 单端登录
            // if (LoginConfigExpander.getSingleAccountLoginFlag()) {
        }

        // 演示环境，跳过记录日志，非演示环境则记录登录日志
        // TODO config模块完善后补充
        // if (!DemoConfigExpander.getDemoEnvFlag()) {
        if (true) {
            // 12. 更新用户登录时间和ip
            sysUserServiceApi.updateUserLoginInfo(loginUser.getUserId(), ip);

            // 13.登录成功日志
            loginLogServiceApi.loginSuccess(loginUser.getUserId(), loginUser.getAccount());
        }

        // 13.1 登录成功，清空用户的错误登录次数
        loginErrorCountCacheApi.remove(loginRequest.getAccount());

        // 14. 填充登录用户信息
        LoginUserHolder.set(loginUser);

        // 15. 组装返回结果
        return new LoginResponse(loginUser.getUserId(), userLoginToken);
    }

    /**
     * 用户密码校验，校验失败会报异常
     *
     * @param validatePassword 是否校验密码
     * @param loginErrorCount  登录密码错误次数
     * @param loginRequest     登录入参
     * @param userValidateInfo 用户校验信息
     */
    private void validateUserPassword(Boolean validatePassword, Integer loginErrorCount, LoginRequest loginRequest, UserValidateDTO userValidateInfo) {
        // 如果不需要校验登录密码，则直接返回
        if (!validatePassword) {
            return;
        }
        // 如果本次登录需要校验密码
        Boolean checkResult = passwordEncryptionStrategy.checkPasswordWithSalt(loginRequest.getPassword(), userValidateInfo.getUserPasswordSalt(), userValidateInfo.getUserPasswordHexed());
        // 校验用户表密码是否正确，如果正确则直接返回
        if (checkResult) {
            return;
        }

        // TODO 临时密钥登录，后续完善
        loginErrorCountCacheApi.put(loginRequest.getAccount(), loginErrorCount + 1);
        // TODO 记录登录失败日志

        throw new AuthException(AuthExceptionEnum.USERNAME_PASSWORD_ERROR);
    }

    /**
     * 如果开启了密码加密配置，则需要进行密码的密文解密，再进行密码校验
     */
    private void decryptRequestPassword(LoginRequest loginRequest) {
        // TODO config模块完善后补充
        // if (loginRequest.getPassword() != null && LoginConfigExpander.getPasswordRsaValidateFlag()) {
        if (false) {
            String decryptPassword = passwordTransferEncryptApi.decrypt(loginRequest.getPassword());
            loginRequest.setPassword(decryptPassword);
        }
    }

    /**
     * 校验用户的图形验证码，或者拖拽验证码是否正确
     */
    private void validateCaptcha(LoginRequest loginRequest) {
        // TODO config模块完善后补充
        // if (SecurityConfigExpander.getCaptchaOpen()) {
        if (true) {
            String verKey = loginRequest.getVerKey();
            String verCode = loginRequest.getVerCode();

            if (StrUtil.hasBlank(verKey, verCode)) {
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_EMPTY);
            }
            if (!captchaApi.validateCaptcha(verKey, verCode)) {
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_ERROR);
            }
        }

        // 验证拖拽验证码
        // TODO config模块完善后补充
        // if (SecurityConfigExpander.getDragCaptchaOpen()) {
        if (false) {
            String verKey = loginRequest.getVerKey();
            String verXLocationValue = loginRequest.getVerCode();
            if (StrUtil.hasBlank(verKey, verXLocationValue)) {
                throw new AuthException(ValidatorExceptionEnum.CAPTCHA_EMPTY);
            }
            if (!dragCaptchaApi.validateCaptcha(verKey, Convert.toInt(verXLocationValue))) {
                throw new AuthException(ValidatorExceptionEnum.DRAG_CAPTCHA_ERROR);
            }
        }
    }

    /**
     * 登录接口，校验登录参数是否为空
     */
    private static void validateEmptyParams(LoginRequest loginRequest, Boolean validatePassword) {
        if (validatePassword) {
            if (loginRequest == null || StrUtil.hasBlank(loginRequest.getAccount(), loginRequest.getPassword())) {
                throw new AuthException(AuthExceptionEnum.PARAM_EMPTY);
            }
        } else {
            if (loginRequest == null || StrUtil.hasBlank(loginRequest.getAccount())) {
                throw new AuthException(AuthExceptionEnum.ACCOUNT_IS_BLANK);
            }
        }
    }

    /**
     * 校验密码重试次数是否过多，默认不能超过5次
     */
    private Integer validatePasswordRetryTimes(LoginRequest loginRequest) {
        Integer loginErrorCount = loginErrorCountCacheApi.get(loginRequest.getAccount());
        // TODO config模块完善后补充
        // if (loginErrorCount != null && loginErrorCount >= LoginConfigExpander.getMaxErrorLoginCount()) {
        if (loginErrorCount != null && loginErrorCount >= 5) {
            // 修改用户状态为锁定，默认锁定一天
            sysUserServiceApi.lockUserStatus(loginRequest.getAccount());
            throw new AuthException(AuthExceptionEnum.LOGIN_LOCKED);
        }
        return 0;
    }

}
