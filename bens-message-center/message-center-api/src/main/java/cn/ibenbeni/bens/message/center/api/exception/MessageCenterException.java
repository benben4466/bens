package cn.ibenbeni.bens.message.center.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.message.center.api.constants.MessageCenterConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

public class MessageCenterException extends ServiceException {

    public MessageCenterException(AbstractExceptionEnum exception) {
        super(MessageCenterConstants.MESSAGE_CENTER_MODULE_NAME, exception);
    }

    public MessageCenterException(AbstractExceptionEnum exception, Object... params) {
        super(MessageCenterConstants.MESSAGE_CENTER_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
