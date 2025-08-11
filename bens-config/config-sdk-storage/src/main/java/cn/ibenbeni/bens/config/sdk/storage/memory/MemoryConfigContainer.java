package cn.ibenbeni.bens.config.sdk.storage.memory;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.config.api.ConfigApi;
import cn.ibenbeni.bens.config.api.exception.enums.ConfigExceptionEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统参数-内存容器
 */
@Slf4j
public class MemoryConfigContainer implements ConfigApi {

    /**
     * 系统参数缓存
     */
    private static final Dict CONFIG_CONTAINER = Dict.create();

    @Override
    public void putConfig(String key, String value) {
        CONFIG_CONTAINER.put(key, value);
    }

    @Override
    public void deleteConfig(String key) {
        CONFIG_CONTAINER.remove(key);
    }

    @Override
    public <T> T getConfigValueNullable(String configCode, Class<T> clazz) {
        String configValue = CONFIG_CONTAINER.getStr(configCode);
        if (StrUtil.isNotBlank(configValue)) {
            try {
                return Convert.convert(clazz, configValue);
            } catch (Exception ex) {
                String format = StrUtil.format(ConfigExceptionEnum.CONVERT_ERROR.getUserTip(), configCode, configValue, clazz.toString());
                log.warn(format);
                return null;
            }
        } else {
            String format = StrUtil.format(ConfigExceptionEnum.CONFIG_NOT_EXIST.getUserTip(), configCode);
            log.warn(format);
            return null;
        }
    }

    @Override
    public <T> T getSysConfigValueWithDefault(String configCode, Class<T> clazz, T defaultValue) {
        T configValue = this.getConfigValueNullable(configCode, clazz);
        return ObjectUtil.isNotEmpty(configValue) ? configValue : defaultValue;
    }

}
