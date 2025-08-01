package cn.ibenbeni.bens.auth.api;

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

}
