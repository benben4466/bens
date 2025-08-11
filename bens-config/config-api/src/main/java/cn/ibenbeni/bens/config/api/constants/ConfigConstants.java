package cn.ibenbeni.bens.config.api.constants;

/**
 * 系统配置表的常量
 *
 * @author: benben
 * @time: 2025/6/18 下午10:51
 */
public interface ConfigConstants {

    /**
     * config模块的名称
     */
    String CONFIG_MODULE_NAME = "bens-config";

    /**
     * 异常枚举的步进值
     */
    String CONFIG_EXCEPTION_STEP_CODE = "04";

    /**
     * 参数配置缓存前缀
     */
    String CONFIG_VALUE_CACHE_PREFIX = "CONFIG:VALUE:";

    /**
     * 默认的Config模块的缓存超时时间
     */
    Long DEFAULT_CONFIG_CACHE_TIMEOUT_SECONDS = 3600L;

    /**
     * 系统初始化编码
     * <p>是sys_config表的config_code字段存储值</p>
     */
    String SYSTEM_CONFIG_INIT_FLAG_CODE = "SYS_CONFIG_INIT_FLAG";

}
