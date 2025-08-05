package cn.ibenbeni.bens.validator.api.validators.enums;

import cn.ibenbeni.bens.rule.base.ReadableEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InEnumValidator implements ConstraintValidator<InEnum, Object> {

    private List<?> values;

    @Override
    public void initialize(InEnum annotation) {
        ReadableEnum<?>[] values = annotation.value().getEnumConstants();
        if (values.length == 0) {
            this.values = Collections.emptyList();
        } else {
            this.values = Arrays.asList(values[0].compareValueArray());
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 为空默认放行
        if (values == null || value == null || values.isEmpty()) {
            return true;
        }

        // 校验通过
        if (values.contains(value)) {
            return true;
        }

        // 校验通过,修改提示信息
        // 禁用默认的提示信息
        context.disableDefaultConstraintViolation();
        // 重新添加提示信息
        context.buildConstraintViolationWithTemplate(
                context.getDefaultConstraintMessageTemplate().replaceAll("\\{value}", values.toString())
        ).addConstraintViolation();

        return false;
    }

}
