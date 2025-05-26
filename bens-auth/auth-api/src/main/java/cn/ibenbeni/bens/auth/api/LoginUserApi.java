package cn.ibenbeni.bens.auth.api;

import cn.ibenbeni.bens.auth.api.exception.AuthException;
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
     * 获取当前登陆用户
     * <p>如果获取不到当前登陆用户会抛出 AuthException</p>
     *
     * @return 当前登陆用户信息
     * @throws AuthException 权限异常
     */
    LoginUser getLoginUser() throws AuthException;

    /**
     * 获取当前登陆用户
     * <p>
     * 如果获取不到当前登陆用户返回null
     *
     * @return 当前登录用户信息
     */
    LoginUser getLoginUserNullable();

    /**
     * 判断当前用户是否登录
     *
     * @return 是否登录，true是，false否
     */
    boolean hasLogin();

    /**
     * 获取是否是超级管理员的标识
     *
     * @return true-是超级管理员，false-不是超级管理员
     */
    boolean getSuperAdminFlag();

    /**
     * 获取当前登录用户当前机构对应的公司id
     */
    Long getCurrentUserCompanyId();

}
