package cn.ibenbeni.bens.ds.api.annotation;

import java.lang.annotation.*;

/**
 * 多数据源标识注解
 *
 * @author: benben
 * @time: 2025/6/25 下午2:04
 */
@Inherited // 允许子类继承
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

    /**
     * 数据源名称
     */
    String name() default "";

}
