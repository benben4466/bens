package cn.ibenbeni.bens.validator.api.validators.phone;

import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号码校验器
 * <p>解释：
 * 1.ConstraintValidator接口：实现校验逻辑
 * 2.ConstraintValidator<A, T>：A是注解类型，T是被校验的字段或参数的类型。
 * 3.在@PhoneValue注解上使用@Constraint注解，并指定该校验器
 * </p>
 *
 * @author: benben
 * @time: 2025/6/14 下午3:44
 */
public class PhoneValueValidator implements ConstraintValidator<PhoneValue, String> {

    /**
     * 是否必填
     * <p>若必填时，值缺失会报错</p>
     */
    private Boolean required;

    /**
     * 初始化校验器
     *
     * @param constraintAnnotation 被注解的实例，可以通过它获取注解的属性值
     */
    @Override
    public void initialize(PhoneValue constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    /**
     * 执行具体的校验逻辑
     *
     * @param phoneValue 被校验的字段或参数的值
     * @param context    校验上下文，用于在复杂校验场景中提供额外信息
     * @return true=校验通过；false=校验失败
     */
    @Override
    public boolean isValid(String phoneValue, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(phoneValue)) {
            return !required;
        } else {
            return PhoneUtil.isMobile(phoneValue);
        }
    }

}
