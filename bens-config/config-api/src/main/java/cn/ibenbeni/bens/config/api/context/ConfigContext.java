package cn.ibenbeni.bens.config.api.context;

import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.config.api.ConfigApi;
import cn.ibenbeni.bens.config.api.exception.ConfigException;
import cn.ibenbeni.bens.config.api.exception.enums.ConfigExceptionEnum;

/**
 * 系统配置上下文
 *
 * @author: benben
 * @time: 2025/6/18 下午11:02
 */
public class ConfigContext {

    private static ConfigApi CONFIG_API = null;

    /**
     * 获取系统配置操作API
     */
    public static ConfigApi me() {
        if (CONFIG_API == null) {
            ConfigApi configApi = SpringUtil.getBean(ConfigApi.class);
            if (configApi != null) {
                CONFIG_API = configApi;
            } else {
                throw new ConfigException(ConfigExceptionEnum.CONFIG_CONTAINER_IS_NULL);
            }
        }
        return CONFIG_API;
    }

    /**
     * 设置系统配置操作API实现
     */
    public static void setConfigApi(ConfigApi configApi) {
        CONFIG_API = configApi;
    }

}
