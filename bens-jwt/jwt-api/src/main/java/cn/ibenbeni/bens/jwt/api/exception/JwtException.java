package cn.ibenbeni.bens.jwt.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.jwt.api.constants.JwtConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

/**
 * JWT异常
 */
public class JwtException extends ServiceException {

    public JwtException(AbstractExceptionEnum exception, Object... params) {
        super(JwtConstants.JWT_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public JwtException(AbstractExceptionEnum exception) {
        super(JwtConstants.JWT_MODULE_NAME, exception);
    }

}
