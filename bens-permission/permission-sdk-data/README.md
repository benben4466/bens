
在需要实现部门数据权限的模块中，创建一个提供DeptDataPermissionRuleCustomizer的Bean并注入IOC中
~~~java
// 部门数据权限自动配置类
@Configuration(proxyBeanMethods = false)
public class DataPermissionConfiguration {

    /**
     * 注入部门数据权限规则定制器
     */
    @Bean
    public DeptDataPermissionRuleCustomizer sysDeptDataPermissionRuleCustomizer() {
        return rule -> {
            rule.addDeptColumn(HrOrganizationDO.class, "org_id");
        };
    }

}
~~~