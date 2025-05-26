package cn.ibenbeni.bens.auth.auth;

import cn.dev33.satoken.stp.StpUtil;
import cn.ibenbeni.bens.auth.api.LoginUserApi;
import cn.ibenbeni.bens.auth.api.SessionManagerApi;
import cn.ibenbeni.bens.auth.api.context.LoginContext;
import cn.ibenbeni.bens.auth.api.context.LoginUserHolder;
import cn.ibenbeni.bens.auth.api.exception.AuthException;
import cn.ibenbeni.bens.auth.api.exception.enums.AuthExceptionEnum;
import cn.ibenbeni.bens.auth.api.loginuser.CommonLoginUserUtil;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.sys.api.SysUserServiceApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 当前登陆用户的接口实现
 *
 * @author benben
 * @date 2025/5/20  下午2:34
 */
@Service
public class LoginUserImpl implements LoginUserApi {

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private SysUserServiceApi sysUserServiceApi;

    @Override
    public String getToken() {
        return CommonLoginUserUtil.getToken();
    }

    @Override
    public LoginUser getLoginUser() throws AuthException {
        // 先从ThreadLocal中获取
        LoginUser currentUser = LoginUserHolder.get();
        if (currentUser != null) {
            return currentUser;
        }

        // 获取用户的token
        String token = getToken();
        // 获取session中该token对应的用户
        LoginUser session = sessionManagerApi.getSession(token);
        // session为空抛出异常
        if (session == null) {
            throw new AuthException(AuthExceptionEnum.AUTH_EXPIRED_ERROR);
        }
        return session;
    }

    @Override
    public LoginUser getLoginUserNullable() {
        // 先从ThreadLocal中获取
        LoginUser currentUser = LoginUserHolder.get();
        if (currentUser != null) {
            return currentUser;
        }

        // 获取用户的token
        String token = null;
        try {
            token = getToken();
        } catch (Exception e) {
            return null;
        }

        // 获取session中该token对应的用户
        return sessionManagerApi.getSession(token);
    }

    @Override
    public boolean hasLogin() {
        return StpUtil.isLogin();
    }

    @Override
    public boolean getSuperAdminFlag() {
        LoginUser loginUser = getLoginUser();
        return sysUserServiceApi.getUserSuperAdminFlag(loginUser.getUserId());
    }

    @Override
    public Long getCurrentUserCompanyId() {
        Long currentOrgId = LoginContext.me().getLoginUser().getCurrentOrgId();
        if (currentOrgId == null) {
            return null;
        }

//        CompanyDeptDTO orgCompanyInfo = organizationServiceApi.getOrgCompanyInfo(currentOrgId);
//        if (orgCompanyInfo == null) {
//            return null;
//        }
        return 111L;
    }

}
