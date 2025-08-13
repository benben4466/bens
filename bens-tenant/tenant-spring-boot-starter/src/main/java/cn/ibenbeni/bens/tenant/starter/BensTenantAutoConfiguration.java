package cn.ibenbeni.bens.tenant.starter;

import cn.ibenbeni.bens.db.mp.util.MyBatisPlusUtils;
import cn.ibenbeni.bens.rule.constants.WebFilterOrderConstants;
import cn.ibenbeni.bens.tenant.aop.TenantIgnoreAspect;
import cn.ibenbeni.bens.tenant.api.prop.TenantProp;
import cn.ibenbeni.bens.tenant.db.TenantDbInterceptor;
import cn.ibenbeni.bens.tenant.web.TenantContextWebFilter;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 租户模块自动配置
 *
 * @author: benben
 * @time: 2025/6/27 下午2:21
 */
@Configuration
@ConditionalOnProperty(prefix = "bens.tenant", value = "enable", matchIfMissing = true) // 多租户模块开关
@EnableConfigurationProperties(TenantProp.class)
public class BensTenantAutoConfiguration {

    /**
     * 注入忽略多租户插件
     */
    @Bean
    public TenantIgnoreAspect tenantIgnoreAspect() {
        return new TenantIgnoreAspect();
    }

    // region DB插件

    /**
     * DB多租户插件
     * <p>基于MyBatisPlus实现</p>
     *
     * @param tenantProp  租户配置
     * @param interceptor MyBatisPlus插件管理器
     * @return 多租户插件
     */
    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantProp tenantProp, MybatisPlusInterceptor interceptor) {
        // 自定义租户处理器
        TenantDbInterceptor tenantDbInterceptor = new TenantDbInterceptor(tenantProp);
        // MyBatisPlus租户拦截器
        TenantLineInnerInterceptor inner = new TenantLineInnerInterceptor(tenantDbInterceptor);
        // 租户插件需在分页插件之前
        MyBatisPlusUtils.addInterceptor(interceptor, inner, 0);
        return inner;
    }

    // endregion

    // region Web过滤器

    /**
     * 注入多租户上下文过滤器
     */
    @Bean
    public FilterRegistrationBean<TenantContextWebFilter> tenantContextWebFilter() {
        FilterRegistrationBean<TenantContextWebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantContextWebFilter());
        registrationBean.setOrder(WebFilterOrderConstants.TENANT_CONTEXT_FILTER);
        return registrationBean;
    }

    // endregion

}
