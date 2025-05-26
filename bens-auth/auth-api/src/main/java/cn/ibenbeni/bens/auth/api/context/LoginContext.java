package cn.ibenbeni.bens.auth.api.context;

import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.auth.api.LoginUserApi;

/**
 * 快速获取当前登陆用户的一系列操作方法，具体实现在Spring容器中查找
 *
 * @author benben
 * @date 2025/5/3  下午11:05
 */
public class LoginContext {

    public static LoginUserApi me() {
        return SpringUtil.getBean(LoginUserApi.class);
    }

}
