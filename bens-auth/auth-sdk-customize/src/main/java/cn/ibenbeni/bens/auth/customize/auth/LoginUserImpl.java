package cn.ibenbeni.bens.auth.customize.auth;

import cn.ibenbeni.bens.auth.api.LoginUserApi;
import cn.ibenbeni.bens.auth.api.context.LoginUserHolder;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.auth.api.util.CommonLoginUserUtils;
import org.springframework.stereotype.Service;

/**
 * 登录用户实现
 */
@Service
public class LoginUserImpl implements LoginUserApi {

    @Override
    public String getToken() {
        return CommonLoginUserUtils.getToken();
    }

    @Override
    public LoginUser getLoginUser() {
        return LoginUserHolder.get();
    }

}
