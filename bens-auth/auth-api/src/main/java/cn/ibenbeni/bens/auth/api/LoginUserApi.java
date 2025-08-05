package cn.ibenbeni.bens.auth.api;

import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;

/**
 * 当前登陆用户相关的一系列方法
 *
 * @author benben
 */
public interface LoginUserApi {

    /**
     * 获取当前登陆用户的token
     * <p>如果获取不到，返回null</p>
     *
     * @return 当前用户的token或null
     */
    String getToken();

    /**
     * 获取当前登陆用户信息
     */
    LoginUser getLoginUser();

}
