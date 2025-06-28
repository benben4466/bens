package cn.ibenbeni.bens.tenant.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.tenant.api.constants.TenantConstants;

/**
 * 租户异常
 *
 * @author: benben
 * @time: 2025/6/27 上午11:09
 */
public class TenantException extends ServiceException {

    public TenantException(AbstractExceptionEnum exception, Object... params) {
        super(TenantConstants.TENANT_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public TenantException(AbstractExceptionEnum exception) {
        super(TenantConstants.TENANT_MODULE_NAME, exception);
    }

}
