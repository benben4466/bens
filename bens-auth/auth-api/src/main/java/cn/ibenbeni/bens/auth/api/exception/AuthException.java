package cn.ibenbeni.bens.auth.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.auth.api.constants.AuthConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

/**
 * 认证类异常
 *
 * @author benben
 * @date 2025/5/3  下午11:06
 */
public class AuthException extends ServiceException {

    public AuthException(AbstractExceptionEnum exception, Object... params) {
        super(AuthConstants.AUTH_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public AuthException(AbstractExceptionEnum exception) {
        super(AuthConstants.AUTH_MODULE_NAME, exception);
    }

}
