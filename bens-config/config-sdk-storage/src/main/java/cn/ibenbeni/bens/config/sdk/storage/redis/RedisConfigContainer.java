package cn.ibenbeni.bens.config.sdk.storage.redis;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.config.api.ConfigApi;
import cn.ibenbeni.bens.config.api.exception.enums.ConfigExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * 系统参数-Redis容器
 */
@Slf4j
public class RedisConfigContainer implements ConfigApi {

    private final String CONFIG_PREFIX = "CONFIG:SYS_CONFIG_CACHE:";

    private RedissonClient redissonClient = null;

    public RedisConfigContainer(String redisHost, Integer redisPort, String redisPassword, Integer dbNumber) {
        if (StrUtil.isBlank(redisHost)) {
            log.info("系统参数初始化Redis连接Host空,默认设置为[127.0.0.1]");
            redisHost = "127.0.0.1";
        }
        if (ObjectUtil.isEmpty(redisPort)) {
            log.info("系统参数初始化Redis连接Port空,默认设置为[6379]");
            redisPort = 6379;
        }
        if (StrUtil.isBlank(redisPassword)) {
            log.info("系统参数初始化Redis连接Password空,默认设置为[null]");
            redisPassword = null;
        }
        if (ObjectUtil.isEmpty(dbNumber)) {
            log.info("系统参数初始化Redis连接DBNumber空,默认设置为[0]");
            dbNumber = 0;
        }

        // 创建Redis连接
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":" + redisPort)
                .setPassword(redisPassword)
                .setDatabase(dbNumber);
        redissonClient = Redisson.create(config);
    }

    @Override
    public void putConfig(String key, String value) {
        redissonClient.getBucket(CONFIG_PREFIX + key).set(value);
    }

    @Override
    public void deleteConfig(String key) {
        redissonClient.getBucket(CONFIG_PREFIX + key).delete();
    }

    @Override
    public <T> T getConfigValueNullable(String configCode, Class<T> clazz) {
        String configValue = redissonClient.getBucket(CONFIG_PREFIX + configCode).get().toString();
        if (StrUtil.isBlank(configValue)) {
            String format = StrUtil.format(ConfigExceptionEnum.CONFIG_NOT_EXIST.getUserTip(), configCode);
            log.warn(format);
            return null;
        } else {
            try {
                return Convert.convert(clazz, configValue);
            } catch (Exception ex) {
                String format = StrUtil.format(ConfigExceptionEnum.CONVERT_ERROR.getUserTip(), configCode, configValue, clazz.toString());
                log.warn(format);
                return null;
            }
        }
    }

    @Override
    public <T> T getSysConfigValueWithDefault(String configCode, Class<T> clazz, T defaultValue) {
        T value = this.getConfigValueNullable(configCode, clazz);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

}
