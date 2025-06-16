package cn.ibenbeni.bens.validator.api.validators.phone;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 校验手机号码注解
 *
 * @author: benben
 * @time: 2025/6/14 下午3:38
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(PhoneValue.List.class)
@Constraint(validatedBy = PhoneValueValidator.class)
public @interface PhoneValue {

    /**
     * 是否必填
     * <p>若必填时，值缺失会报错</p>
     */
    boolean required() default true;

    /**
     * 提示信息
     */
    String message() default "手机号码格式错误";

    /**
     * 校验参数分组
     */
    Class[] groups() default {};

    /**
     * 校验注解的"负载"
     * <p>负载是一个可选的扩展机制，允许你将额外的信息与校验注解关联起来。</p>
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 原因：Java 不允许直接在同一个字段上重复使用同一个注解。
     * <p>定义List作用：允许在同一个字段或参数上应用多个相同的校验注解。</p>
     * <p>使用方法一：
     *      @PhoneValue.List({
     *          @PhoneValue(message="用户不能在Group1分组为空", groups={Group1.class})
     *          @PhoneValue(message="用户不能在Group2分组为空", groups={Group2.class})
     *      })
     * </p>
     * <p>使用方式二：
     *     加入@Repeatable注解并指定注解容器后，使用方式：可直接在字段上加注解，不用@PhoneValue.List({})
     *          @PhoneValue(message="用户不能在Group1分组为空", groups={Group1.class})
     *          @PhoneValue(message="用户不能在Group2分组为空", groups={Group2.class})
     * </p>
     */
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        PhoneValue[] value();
    }

}
