package cn.ibenbeni.bens.dict.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.dict.api.constants.DictConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

/**
 * 字典模块的异常
 *
 * @author: benben
 * @time: 2025/6/14 上午10:35
 */
public class DictException extends ServiceException {

    public DictException(AbstractExceptionEnum exception, Object... params) {
        super(DictConstants.DICT_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public DictException(AbstractExceptionEnum exception) {
        super(DictConstants.DICT_MODULE_NAME, exception);
    }

}
