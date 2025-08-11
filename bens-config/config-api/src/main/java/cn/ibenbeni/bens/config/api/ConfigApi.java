package cn.ibenbeni.bens.config.api;

/**
 * @author: benben
 * @time: 2025/6/18 下午11:03
 */
public interface ConfigApi {

    /**
     * 添加系统参数配置
     *
     * @param key   参数编码
     * @param value 参数值
     */
    void putConfig(String key, String value);

    /**
     * 删除系统参数配置
     *
     * @param key 系统参数配置编码
     */
    void deleteConfig(String key);

    /**
     * 获取sys_config表中的配置，如果为空，返回null
     *
     * @param configCode 系统参数配置编码
     * @param clazz      返回值类型
     * @param <T>        返回值类型
     */
    <T> T getConfigValueNullable(String configCode, Class<T> clazz);

    /**
     * 获取sys_config表中的配置，如果为空返回默认值
     *
     * @param configCode   系统参数配置编码
     * @param clazz        返回值类型
     * @param defaultValue 如果结果为空，返回默认值
     * @param <T>          返回值类型
     */
    <T> T getSysConfigValueWithDefault(String configCode, Class<T> clazz, T defaultValue);

}
