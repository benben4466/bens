package cn.ibenbeni.bens.auth.customize.auth;

import cn.ibenbeni.bens.auth.api.LoginUserApi;
import cn.ibenbeni.bens.auth.api.context.LoginUserHolder;
import org.springframework.stereotype.Service;

/**
 * 登录用户实现
 */
@Service
public class LoginUserImpl implements LoginUserApi {

    @Override
    public String getToken() {
        return LoginUserHolder.get().getToken();
    }

}
