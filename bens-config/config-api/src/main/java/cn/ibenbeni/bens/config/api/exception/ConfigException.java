package cn.ibenbeni.bens.config.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.config.api.constants.ConfigConstants;
import cn.ibenbeni.bens.rule.exception.AbstractExceptionEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;

/**
 * 系统配置表的异常
 *
 * @author: benben
 * @time: 2025/6/18 下午10:52
 */
public class ConfigException extends ServiceException {

    public ConfigException(AbstractExceptionEnum exception, Object... params) {
        super(ConfigConstants.CONFIG_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public ConfigException(AbstractExceptionEnum exception) {
        super(ConfigConstants.CONFIG_MODULE_NAME, exception);
    }

}
