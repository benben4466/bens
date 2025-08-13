package cn.ibenbeni.bens.tenant.api.annotation;

import cn.ibenbeni.bens.tenant.api.prop.TenantProp;

import java.lang.annotation.*;

/**
 * 忽略租户,标记指定方法不执行租户过滤条件
 * <p>
 * 注意：
 * 1.DB场景忽略租户
 * <p>
 * 说明：
 * 1.若DO实体上添加此注解，则相当于将表名添加到{@link TenantProp#ignoreTables}中
 * </p>
 */
@Target({ElementType.METHOD, ElementType.TYPE}) // 方法或者类级别
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TenantIgnore {

    /**
     * 是否开启忽略租户，默认true
     */
    String enable() default "true";

}
