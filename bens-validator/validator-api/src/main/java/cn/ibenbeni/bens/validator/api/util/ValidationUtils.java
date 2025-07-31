package cn.ibenbeni.bens.validator.api.util;

import cn.hutool.core.collection.CollUtil;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * 校验工具类
 */
public class ValidationUtils {

    /**
     * 手动校验字段约束
     * <p>校验{@link javax.validation.constraints.NotNull}等注解</p>
     *
     * @param validator      校验器
     * @param object         被校验对象
     * @param validateGroups 可选参数，指定验证组。如果没有指定验证组，则使用默认组（Default）。
     */
    public static void validate(Validator validator, Object object, Class<?>... validateGroups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, validateGroups);
        if (CollUtil.isNotEmpty(constraintViolations)) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

}
