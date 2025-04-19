package cn.ibenbeni.bens.db.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.db.api.constants.DbConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

/**
 * 数据库操作异常
 *
 * @author benben
 * @date 2025/4/18  上午11:31
 */
public class DaoException extends ServiceException {

    public DaoException(AbstractExceptionEnum exception) {
        super(DbConstants.DB_MODULE_NAME, exception);
    }

    public DaoException(AbstractExceptionEnum exception, Object... params) {
        super(DbConstants.DB_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
