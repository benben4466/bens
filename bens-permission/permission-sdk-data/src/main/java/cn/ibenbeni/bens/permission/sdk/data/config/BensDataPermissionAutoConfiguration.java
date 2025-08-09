package cn.ibenbeni.bens.permission.sdk.data.config;

import cn.ibenbeni.bens.db.mp.util.MyBatisPlusUtils;
import cn.ibenbeni.bens.permission.sdk.data.aop.DataPermissionAnnotationAdvisor;
import cn.ibenbeni.bens.permission.sdk.data.handler.DataPermissionRuleHandler;
import cn.ibenbeni.bens.permission.sdk.data.helper.DataPermissionRuleHelper;
import cn.ibenbeni.bens.permission.sdk.data.rule.DataPermissionRule;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 数据权限自动配置类
 */
@Configuration
public class BensDataPermissionAutoConfiguration {

    /**
     * 注入数据权限规则-Helper
     *
     * @param rules 数据权限规则
     */
    @Bean
    public DataPermissionRuleHelper dataPermissionRuleHelper(List<DataPermissionRule> rules) {
        return new DataPermissionRuleHelper(rules);
    }

    /**
     * 注入数据权限注解切面
     */
    @Bean
    public DataPermissionAnnotationAdvisor dataPermissionAnnotationAdvisor() {
        return new DataPermissionAnnotationAdvisor();
    }

    @Bean
    public DataPermissionRuleHandler dataPermissionRuleHandler(MybatisPlusInterceptor interceptor,
                                                               DataPermissionRuleHelper dataPermissionRuleHelper) {
        DataPermissionRuleHandler handler = new DataPermissionRuleHandler(dataPermissionRuleHelper);
        DataPermissionInterceptor inner = new DataPermissionInterceptor(handler);
        // 添加到MyBatisPlus拦截器链中，需要加在分页插件之前
        MyBatisPlusUtils.addInterceptor(interceptor, inner, 0);
        return handler;
    }

}
