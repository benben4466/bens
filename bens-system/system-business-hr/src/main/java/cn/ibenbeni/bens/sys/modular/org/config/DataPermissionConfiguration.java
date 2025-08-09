package cn.ibenbeni.bens.sys.modular.org.config;

import cn.ibenbeni.bens.permission.sdk.data.rule.dept.DeptDataPermissionRuleCustomizer;
import cn.ibenbeni.bens.sys.modular.org.entity.HrOrganizationDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 部门数据权限自动配置类
 */
//@Configuration(proxyBeanMethods = false)
//public class DataPermissionConfiguration {
//
//    /**
//     * 注入部门数据权限规则定制器
//     */
//    @Bean
//    public DeptDataPermissionRuleCustomizer sysDeptDataPermissionRuleCustomizer() {
//        return rule -> {
//            rule.addDeptColumn(HrOrganizationDO.class, "org_id");
//        };
//    }
//
//}
