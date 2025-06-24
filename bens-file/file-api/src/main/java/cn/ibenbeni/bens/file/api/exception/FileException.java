package cn.ibenbeni.bens.file.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.file.api.constants.FileConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

/**
 * 文件异常
 *
 * @author: benben
 * @time: 2025/6/22 上午9:22
 */
public class FileException extends ServiceException {

    public FileException(AbstractExceptionEnum exception) {
        super(FileConstants.FILE_MODULE_NAME, exception);
    }

    public FileException(AbstractExceptionEnum exception, Object... params) {
        super(FileConstants.FILE_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
