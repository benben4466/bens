package cn.ibenbeni.bens.auth.starter;

import cn.ibenbeni.bens.auth.api.SessionManagerApi;
import cn.ibenbeni.bens.auth.api.constants.PasswordEncryptionConstants;
import cn.ibenbeni.bens.auth.api.expander.AuthConfigExpander;
import cn.ibenbeni.bens.auth.api.password.PasswordEncryptionStrategy;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.auth.customize.filter.TokenAuthenticationFilter;
import cn.ibenbeni.bens.auth.customize.password.MD5PasswordEncryptionStrategy;
import cn.ibenbeni.bens.auth.customize.session.DefaultSessionManager;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.jwt.JwtTokenOperator;
import cn.ibenbeni.bens.jwt.api.JwtApi;
import cn.ibenbeni.bens.jwt.api.pojo.config.JwtConfig;
import cn.ibenbeni.bens.rule.constants.WebFilterOrderConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
     * JWT操作API
     */
    @Bean
    @ConditionalOnMissingBean(JwtApi.class)
    public JwtApi jwtApi() {
        JwtConfig jwtConfig = new JwtConfig();
        jwtConfig.setJwtSecret(AuthConfigExpander.getAuthJwtSecret());
        jwtConfig.setExpiredSeconds(AuthConfigExpander.getSessionExpiredSeconds());
        return new JwtTokenOperator(jwtConfig);
    }

    /**
     * 默认的session缓存为内存缓存，方便启动
     * <p>如需替换请在项目中增加一个SessionManagerApi Bean即可</p>
     */
    @Bean
    @ConditionalOnMissingBean(SessionManagerApi.class)
    public SessionManagerApi sessionManagerApi(CacheOperatorApi<LoginUser> loginUserCache, CacheOperatorApi<String> loginTokenCache) {
        Long sessionExpiredSeconds = AuthConfigExpander.getSessionExpiredSeconds();
        return new DefaultSessionManager(loginUserCache, loginTokenCache, sessionExpiredSeconds);
    }

    /**
     * 注入Token认证过滤器
     */
    @Bean
    public FilterRegistrationBean<TokenAuthenticationFilter> tokenAuthenticationFilterFilterRegistrationBean() {
        FilterRegistrationBean<TokenAuthenticationFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new TokenAuthenticationFilter());
        // TODO 提供各模块拓展接口
        bean.addUrlPatterns("/*");
        bean.setName(TokenAuthenticationFilter.NAME);
        bean.setOrder(WebFilterOrderConstants.TOKEN_AUTHENTICATION_FILTER);
        return bean;
    }

    /**
     * 注入MD5密码加密策略
     */
    @Bean
    @ConditionalOnProperty(name = "bens.security.password", havingValue = PasswordEncryptionConstants.MD5)
    public PasswordEncryptionStrategy passwordEncryptionStrategy() {
        return new MD5PasswordEncryptionStrategy();
    }

}
