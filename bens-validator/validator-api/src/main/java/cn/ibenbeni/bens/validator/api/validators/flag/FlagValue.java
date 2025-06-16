package cn.ibenbeni.bens.validator.api.validators.flag;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验标识
 * <p>校验Y和N两种状态的标识</p>
 *
 * @author: benben
 * @time: 2025/6/15 下午3:14
 */
@Constraint(validatedBy = FlagValueValidator.class)
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FlagValue {

    /**
     * 是否必填
     */
    boolean required() default true;

    String message() default "不正确的flag标识，请传递Y或者N";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        FlagValue[] value();
    }

}
