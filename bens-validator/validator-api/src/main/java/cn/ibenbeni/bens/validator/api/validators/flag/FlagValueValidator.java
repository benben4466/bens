package cn.ibenbeni.bens.validator.api.validators.flag;

import cn.ibenbeni.bens.rule.enums.YesOrNotEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验标识校验器
 * <p>校验Y和N两种状态的标识</p>
 *
 * @author: benben
 * @time: 2025/6/15 下午3:15
 */
public class FlagValueValidator implements ConstraintValidator<FlagValue, String> {

    /**
     * 是否必填
     */
    private Boolean required;

    @Override
    public void initialize(FlagValue constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            return YesOrNotEnum.Y.getCode().equals(value) || YesOrNotEnum.N.getCode().equals(value);
        } else {
            // 非必填，允许为空
            if (value == null) {
                return true;
            } else {
                return YesOrNotEnum.Y.getCode().equals(value) || YesOrNotEnum.N.getCode().equals(value);
            }
        }
    }

}
