package cn.ibenbeni.bens.auth.customize.auth;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.auth.api.SessionManagerApi;
import cn.ibenbeni.bens.auth.api.exception.AuthException;
import cn.ibenbeni.bens.auth.api.exception.enums.AuthExceptionEnum;
import cn.ibenbeni.bens.auth.api.password.PasswordEncryptionStrategy;
import cn.ibenbeni.bens.auth.api.pojo.auth.AuthLoginReq;
import cn.ibenbeni.bens.auth.api.pojo.auth.AuthLoginResp;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.auth.api.pojo.payload.DefaultJwtPayload;
import cn.ibenbeni.bens.auth.customize.token.TokenService;
import cn.ibenbeni.bens.rule.util.DateUtils;
import cn.ibenbeni.bens.sys.api.SysUserServiceApi;
import cn.ibenbeni.bens.sys.api.enums.user.UserStatusEnum;
import cn.ibenbeni.bens.sys.api.pojo.user.UserValidateDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 登录相关的逻辑封装
 *
 * @author benben
 */
@Service
public class LoginService {

    @Resource
    private SysUserServiceApi sysUserServiceApi;

    @Resource
    private PasswordEncryptionStrategy passwordEncryptionStrategy;

    @Resource
    private TokenService tokenService;

    @Resource
    private SessionManagerApi sessionManagerApi;

    /**
     * 账号密码登陆
     */
    public AuthLoginResp loginAction(AuthLoginReq loginReq) {
        // 校验登陆参数
        validateLoginParams(loginReq);

        // 根据账号获取用户信息
        UserValidateDTO userValidateInfo = sysUserServiceApi.getUserLoginValidateInfo(loginReq.getAccount());

        // 校验用户状态
        if (!UserStatusEnum.ENABLE.getCode().equals(userValidateInfo.getUserStatus())) {
            throw new AuthException(AuthExceptionEnum.USER_STATUS_ERROR, UserStatusEnum.getCodeMessage(userValidateInfo.getUserStatus()));
        }

        // 校验用户密码
        validateUserPassword(loginReq, userValidateInfo);

        // 创建Token令牌
        return createTokenAfterLoginSuccess(userValidateInfo.getUserId(), loginReq.getAccount());
    }

    /**
     * 校验登陆参数
     */
    private void validateLoginParams(AuthLoginReq loginReq) {
        if (loginReq == null || StrUtil.hasBlank(loginReq.getAccount(), loginReq.getPassword())) {
            throw new AuthException(AuthExceptionEnum.PARAM_EMPTY);
        }
    }

    private void validateUserPassword(AuthLoginReq loginReq, UserValidateDTO userValidateInfo) {
        // 校验用户密码
        Boolean checkResult = passwordEncryptionStrategy.checkPasswordWithSalt(loginReq.getPassword(), userValidateInfo.getUserPasswordSalt(), userValidateInfo.getUserPasswordHexed());
        if (!checkResult) {
            throw new AuthException(AuthExceptionEnum.USERNAME_PASSWORD_ERROR);
        }
    }

    private AuthLoginResp createTokenAfterLoginSuccess(Long userId, String account) {
        DefaultJwtPayload defaultJwtPayload = DefaultJwtPayload.builder()
                .userId(userId)
                .account(account)
                .build();
        String accessToken = tokenService.createAccessToken(defaultJwtPayload);

        synchronized (account.intern()) {
            // 使用系统默认时区【注意】
            LoginUser loginUser = LoginUser.builder()
                    .userId(userId)
                    .account(account)
                    .token(accessToken)
                    .expiresTime(DateUtils.convertLocalDateTime(defaultJwtPayload.getExpirationTimestamp()))
                    .build();
            // 缓存用户登录信息及创建会话
            sessionManagerApi.createSession(loginUser);
        }
        return new AuthLoginResp(userId, accessToken);
    }

}
