package cn.ibenbeni.bens.config.api;

/**
 * @author: benben
 * @time: 2025/6/18 下午11:03
 */
public interface ConfigApi {

    /**
     * 保存/更新参数配置
     * <p>缓存操作</p>
     * <p>key=ConfigCode；value=ConfigValue</p>
     */
    void putConfig(String key, String value);

    /**
     * 删除参数配置
     * <p>缓存操作</p>
     *
     * @param key ConfigCode
     */
    void deleteConfig(String key);

}
