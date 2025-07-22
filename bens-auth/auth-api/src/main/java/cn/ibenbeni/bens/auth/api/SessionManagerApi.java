package cn.ibenbeni.bens.auth.api;

import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;

/**
 * <p>用户会话管理</p>
 * <p>会话指的是用户登录后和服务器一直保持一个交互状态的维护</p>
 * <p>会话具有时效性，反之，当用户不再访问系统的时候，会话应该自动失效</p>
 *
 * @author benben
 */
public interface SessionManagerApi {

    /**
     * 创建会话
     *
     * @param loginUser 登录用户
     */
    void createSession(LoginUser loginUser);

    /**
     * 根据Token删除一个会话
     */
    void removeSession(String token);

    /**
     * 通过token获取会话
     *
     * @param token 用户token
     * @return token对应用户的详细信息
     */
    LoginUser getSession(String token);

}
