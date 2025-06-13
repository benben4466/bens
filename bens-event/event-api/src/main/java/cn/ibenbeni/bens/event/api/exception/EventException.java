package cn.ibenbeni.bens.event.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.event.api.constants.EventConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

/**
 * 业务事件异常
 *
 * @author: benben
 * @time: 2025/6/12 下午9:02
 */
public class EventException extends ServiceException {

    public EventException(AbstractExceptionEnum exception, Object... params) {
        super(EventConstants.EVENT_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public EventException(AbstractExceptionEnum exception) {
        super(EventConstants.EVENT_MODULE_NAME, exception);
    }

}
