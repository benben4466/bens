package cn.ibenbeni.bens.resource.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.resource.api.constants.ResourceConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

/**
 * 资源模块异常
 */
public class ResourceException extends ServiceException {

    public ResourceException(AbstractExceptionEnum exception) {
        super(ResourceConstants.RESOURCE_MODULE_NAME, exception);
    }

    public ResourceException(AbstractExceptionEnum exception, Object... params) {
        super(ResourceConstants.RESOURCE_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
