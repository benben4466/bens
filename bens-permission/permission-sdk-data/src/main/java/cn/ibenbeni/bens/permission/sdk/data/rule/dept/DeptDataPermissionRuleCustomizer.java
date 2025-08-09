package cn.ibenbeni.bens.permission.sdk.data.rule.dept;

/**
 * 自定义部门权限规则
 */
@FunctionalInterface
public interface DeptDataPermissionRuleCustomizer {

    /**
     * 自定义部门权限规则
     */
    void customize(DeptDataPermissionRule rule);

}
