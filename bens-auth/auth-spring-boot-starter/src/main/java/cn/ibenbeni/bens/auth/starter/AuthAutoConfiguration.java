package cn.ibenbeni.bens.auth.starter;

import cn.ibenbeni.bens.auth.api.SessionManagerApi;
import cn.ibenbeni.bens.auth.api.expander.AuthConfigExpander;
import cn.ibenbeni.bens.auth.api.password.PasswordTransferEncryptApi;
import cn.ibenbeni.bens.auth.password.DefaultPasswordTransferEncrypt;
import cn.ibenbeni.bens.auth.session.DefaultSessionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 认证和鉴权模块的自动配置
 *
 * @author benben
 * @date 2025/5/25  下午2:13
 */
@Configuration
public class AuthAutoConfiguration {

    /**
     * 默认的session缓存为内存缓存，方便启动
     * <p>如需替换请在项目中增加一个SessionManagerApi Bean即可</p>
     */
    @Bean
    @ConditionalOnMissingBean(SessionManagerApi.class)
    public SessionManagerApi sessionManagerApi() {
        Long sessionExpiredSeconds = AuthConfigExpander.getSessionExpiredSeconds();
        return new DefaultSessionManager(sessionExpiredSeconds);
    }

    /**
     * 密码传输加密Bean
     * <p>暂时未用到</p>
     */
    @Bean
    @ConditionalOnMissingBean(PasswordTransferEncryptApi.class)
    public PasswordTransferEncryptApi passwordTransferEncryptApi() {
        return new DefaultPasswordTransferEncrypt();
    }

}
