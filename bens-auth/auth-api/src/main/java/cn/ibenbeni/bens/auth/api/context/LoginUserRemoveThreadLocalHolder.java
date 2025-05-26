package cn.ibenbeni.bens.auth.api.context;

import cn.ibenbeni.bens.rule.threadlocal.RemoveThreadLocalApi;
import org.springframework.stereotype.Component;

/**
 * 清除当前登录用户相关的ThreadLocalHolder
 *
 * @author benben
 * @date 2025/5/20  下午10:15
 */
@Component
public class LoginUserRemoveThreadLocalHolder implements RemoveThreadLocalApi {

    @Override
    public void removeThreadLocalAction() {
        LoginUserHolder.remove();
    }

}
