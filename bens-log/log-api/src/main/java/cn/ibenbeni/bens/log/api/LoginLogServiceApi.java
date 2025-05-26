package cn.ibenbeni.bens.log.api;

/**
 * 登录日志api接口
 *
 * @author benben
 * @date 2025/5/20  下午9:03
 */
public interface LoginLogServiceApi {

    /**
     * 增加登录成功日志
     *
     * @param userId 用户ID
     */
    void loginSuccess(Long userId, String account);

    /**
     * 增加退出成功日志
     *
     * @param userId 用户ID
     */
    void loginOutSuccess(Long userId);

}
