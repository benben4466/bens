package cn.ibenbeni.bens.excel.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.excel.api.constants.ExcelConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

/**
 * Excel模块异常
 *
 * @author: benben
 * @time: 2025/7/6 下午4:18
 */
public class ExcelException extends ServiceException {

    public ExcelException(AbstractExceptionEnum exception, Object... params) {
        super(ExcelConstants.EXCEL_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public ExcelException(AbstractExceptionEnum exception) {
        super(ExcelConstants.EXCEL_MODULE_NAME, exception);
    }

}
