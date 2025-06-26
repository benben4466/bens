package cn.ibenbeni.bens.ds.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.ds.api.constants.DatasourceContainerConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

/**
 * 数据源容器操作异常
 *
 * @author: benben
 * @time: 2025/6/25 下午2:01
 */
public class DatasourceContainerException extends ServiceException {

    public DatasourceContainerException(AbstractExceptionEnum exception, Object... params) {
        super(DatasourceContainerConstants.DS_CTN_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public DatasourceContainerException(AbstractExceptionEnum exception) {
        super(DatasourceContainerConstants.DS_CTN_MODULE_NAME, exception);
    }

}
