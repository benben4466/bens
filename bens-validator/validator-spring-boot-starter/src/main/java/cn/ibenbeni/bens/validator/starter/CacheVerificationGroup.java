package cn.ibenbeni.bens.validator.starter;

import cn.ibenbeni.bens.validator.api.context.RequestGroupContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 缓存当前控制器使用的校验分组
 *
 * @author: benben
 * @time: 2025/6/15 下午4:32
 */
@Slf4j
public class CacheVerificationGroup extends LocalValidatorFactoryBean {

    /**
     * 检查一个对象是否满足指定的约束条件
     * <p>validationHints中存储当前正在校验的分组的Class类型</p>
     *
     * @param target          需要进行验证的目标对象
     * @param errors          用于存储验证错误的 Errors 对象，包含了所有校验失败的错误信息。
     * @param validationHints 一个可变参数（Object...），它允许你传递额外的校验提示信息（可能是一些用来控制验证行为的参数，例如校验分组）。
     */
    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        try {
            super.validate(target, errors, validationHints);

            // 存储当前请求使用的校验分组
            if (validationHints.length > 0) {
                if (validationHints[0] instanceof Class) {
                    RequestGroupContext.set((Class<?>) validationHints[0]);
                }
            }
        } finally {
            RequestGroupContext.clear();
        }
    }

}
