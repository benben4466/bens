package cn.ibenbeni.bens.sys.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.sys.api.constants.SysConstants;

/**
 * 基础核心业务异常
 *
 * @author benben
 * @date 2025/5/3  下午4:30
 */
public class SysException extends ServiceException {

    public SysException(AbstractExceptionEnum exception, Object... params) {
        super(SysConstants.SYS_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public SysException(AbstractExceptionEnum exception) {
        super(SysConstants.SYS_MODULE_NAME, exception);
    }

}
