package cn.ibenbeni.bens.iot.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.iot.api.constants.IotConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

/**
 * IOT模块异常
 */
public class IotException extends ServiceException {

    public IotException(AbstractExceptionEnum exception) {
        super(IotConstants.IOT_MODULE_NAME, exception);
    }

    public IotException(AbstractExceptionEnum exception, Object... params) {
        super(IotConstants.IOT_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
