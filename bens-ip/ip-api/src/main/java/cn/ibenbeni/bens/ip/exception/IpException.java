package cn.ibenbeni.bens.ip.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.ip.constants.IpConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

/**
 * Ip模块异常
 */
public class IpException extends ServiceException {

    public IpException(AbstractExceptionEnum exception, Object... params) {
        super(IpConstants.IP_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public IpException(AbstractExceptionEnum exception) {
        super(IpConstants.IP_MODULE_NAME, exception);
    }

}
