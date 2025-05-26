package cn.ibenbeni.bens.auth.api.context;

import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;

/**
 * 当前登录用户的临时保存容器
 *
 * @author benben
 * @date 2025/5/20  下午2:37
 */
public class LoginUserHolder {

    // 当前登陆用户
    private static final ThreadLocal<LoginUser> LONGIN_USER_HOLDER = new ThreadLocal<>();

    public static void set(LoginUser abstractLoginUser) {
        LONGIN_USER_HOLDER.set(abstractLoginUser);
    }

    public static LoginUser get() {
        return LONGIN_USER_HOLDER.get();
    }

    public static void remove() {
        LONGIN_USER_HOLDER.remove();
    }

}
