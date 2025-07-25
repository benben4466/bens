package cn.ibenbeni.bens.event.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务事件监听注解
 *
 * @author: benben
 * @time: 2025/6/12 下午8:57
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessListener {

    /**
     * 业务编码或标识
     */
    String businessCode() default "";

}
