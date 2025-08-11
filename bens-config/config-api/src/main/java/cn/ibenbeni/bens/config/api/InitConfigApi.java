package cn.ibenbeni.bens.config.api;

/**
 * 系统配置初始化接口
 */
public interface InitConfigApi {

    /**
     * 获取系统配置是否初始化标志
     *
     * @return true=系统已初始化；false=系统未初始化
     */
    Boolean getInitConfigFlag();

}
