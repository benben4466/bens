package cn.ibenbeni.bens.security.starter;

import cn.ibenbeni.bens.rule.constants.WebFilterOrderConstants;
import cn.ibenbeni.bens.security.api.constants.SecurityConstants;
import cn.ibenbeni.bens.security.clear.ClearThreadLocalFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // 创建 CorsConfiguration 对象
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // 设置访问源地址
        config.addAllowedHeader("*"); // 设置访问源请求头
        config.addAllowedMethod("*"); // 设置访问源请求方法
        // 创建 UrlBasedCorsConfigurationSource 对象
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 对接口配置跨域设置

        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(new CorsFilter(source));
        registrationBean.setOrder(WebFilterOrderConstants.CORS_FILTER);
        return registrationBean;
    }

}
