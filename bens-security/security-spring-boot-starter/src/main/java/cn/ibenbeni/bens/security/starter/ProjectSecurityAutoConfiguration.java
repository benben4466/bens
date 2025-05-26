package cn.ibenbeni.bens.security.starter;

import cn.ibenbeni.bens.security.api.constants.SecurityConstants;
import cn.ibenbeni.bens.security.clear.ClearThreadLocalFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * 安全模块自动配置
 *
 * @author benben
 * @date 2025/5/21  下午8:00
 */
@Configuration
public class ProjectSecurityAutoConfiguration {

    /**
     * ThreadLocal清除器
     */
    @Bean
    public FilterRegistrationBean<ClearThreadLocalFilter> clearThreadLocalFilterFilterRegistrationBean() {
        FilterRegistrationBean<ClearThreadLocalFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ClearThreadLocalFilter());
        bean.addUrlPatterns(SecurityConstants.DEFAULT_XSS_PATTERN);
        bean.setName(ClearThreadLocalFilter.NAME);
        bean.setOrder(HIGHEST_PRECEDENCE + 1);
        return bean;
    }

}
