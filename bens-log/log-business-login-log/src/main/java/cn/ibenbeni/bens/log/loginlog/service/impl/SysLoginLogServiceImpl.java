package cn.ibenbeni.bens.log.loginlog.service.impl;

import cn.ibenbeni.bens.log.api.LoginLogServiceApi;
import org.springframework.stereotype.Service;

/**
 * 系统登录日志服务实现类
 *
 * @author benben
 * @date 2025/5/20  下午9:07
 */
@Service
public class SysLoginLogServiceImpl implements LoginLogServiceApi {

    @Override
    public void loginSuccess(Long userId, String account) {
        // TODO 待实现
    }

    @Override
    public void loginOutSuccess(Long userId) {
        // TODO 待实现
    }

}
