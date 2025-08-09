package cn.ibenbeni.bens.permission.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.permission.api.constants.PermissionConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

public class PermissionException extends ServiceException {

    public PermissionException(AbstractExceptionEnum exception) {
        super(PermissionConstants.PERMISSION_MODULE_NAME, exception);
    }

    public PermissionException(AbstractExceptionEnum exception, Object... params) {
        super(PermissionConstants.PERMISSION_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
