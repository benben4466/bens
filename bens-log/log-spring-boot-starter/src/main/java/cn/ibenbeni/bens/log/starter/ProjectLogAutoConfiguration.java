package cn.ibenbeni.bens.log.starter;

import cn.ibenbeni.bens.log.api.LoginLogServiceApi;
import cn.ibenbeni.bens.log.loginlog.service.impl.SysLoginLogServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 系统日志的自动配置
 *
 * @author benben
 * @date 2025/5/25  下午3:51
 */
@Configuration
public class ProjectLogAutoConfiguration {

    /**
     * 注入登录日志服务Bean
     * <p>暂时</p>
     */
    // @Bean
    // @ConditionalOnMissingBean(LoginLogServiceApi.class)
    public LoginLogServiceApi loginLogServiceApi() {
        return new SysLoginLogServiceImpl();
    }

}
