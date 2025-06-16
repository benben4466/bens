package cn.ibenbeni.bens.validator.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.validator.api.constants.ValidatorConstants;

/**
 * 参数校验异常
 *
 * @author: benben
 * @time: 2025/6/14 下午3:37
 */
public class ParamValidateException extends ServiceException {

    public ParamValidateException(AbstractExceptionEnum exception, Object... params) {
        super(ValidatorConstants.VALIDATOR_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public ParamValidateException(AbstractExceptionEnum exception) {
        super(ValidatorConstants.VALIDATOR_MODULE_NAME, exception);
    }

}
