package cn.ibenbeni.bens.permission.sdk.data.config;

import cn.ibenbeni.bens.auth.api.LoginUserApi;
import cn.ibenbeni.bens.permission.sdk.data.rule.dept.DeptDataPermissionRule;
import cn.ibenbeni.bens.permission.sdk.data.rule.dept.DeptDataPermissionRuleCustomizer;
import cn.ibenbeni.bens.sys.api.PermissionApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 基于部门数据权限的自动配置
 */
@Configuration
@ConditionalOnBean(value = {DeptDataPermissionRuleCustomizer.class})
public class BensDeptDataPermissionAutoConfiguration {

    /**
     * 注入数据权限规则
     * <p>从部门数据权限规则提供者中加载部门数据权限规则</p>
     *
     * @param loginUserApi  登陆用户API
     * @param permissionApi 权限API
     * @param customizers   自定义部门数据权限规则提供者
     */
    @Bean
    public DeptDataPermissionRule deptDataPermissionRule(LoginUserApi loginUserApi,
                                                         PermissionApi permissionApi,
                                                         List<DeptDataPermissionRuleCustomizer> customizers) {
        DeptDataPermissionRule rule = new DeptDataPermissionRule(loginUserApi, permissionApi);
        customizers.forEach(customizer -> customizer.customize(rule));
        return rule;
    }

}
