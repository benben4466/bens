package cn.ibenbeni.bens.security.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.security.api.constants.SecurityConstants;

/**
 * 安全模块异常
 *
 * @author benben
 * @date 2025/5/20  下午4:32
 */
public class SecurityException extends ServiceException {

    public SecurityException(AbstractExceptionEnum exception, Object... params) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public SecurityException(AbstractExceptionEnum exception) {
        super(SecurityConstants.SECURITY_MODULE_NAME, exception);
    }

}
