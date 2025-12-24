package cn.ibenbeni.bens.iot.api.core.tdengine.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;

/**
 * Tdengine数据源注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS("tdengine")
public @interface TDengineDS {
}
