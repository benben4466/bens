package cn.ibenbeni.bens.config.api;

/**
 * 配置初始化回调
 */
public interface ConfigInitCallbackApi {

    /**
     * 初始化之前调用
     */
    void initBefore();

    /**
     * 初始化之后调用
     */
    void initAfter();

}
