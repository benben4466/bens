package cn.ibenbeni.bens.permission.sdk.data.annotation;

import cn.ibenbeni.bens.permission.sdk.data.rule.DataPermissionRule;

import java.lang.annotation.*;

/**
 * 数据权限注解
 * <p>注：即使不添加@DataPermission注解，默认开启</p>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /**
     * 是否启用
     */
    boolean enable() default true;

    /**
     * 生效的数据权限规则数组
     * <p>优先级高于{@link #excludeRules()}</p>
     */
    Class<? extends DataPermissionRule>[] includeRules() default {};

    /**
     * 排除的数据权限规则数组
     */
    Class<? extends DataPermissionRule>[] excludeRules() default {};

}
