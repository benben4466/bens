package cn.ibenbeni.bens.common.chain.core;

/**
 * 责任链上下文接口
 * 定义责任链模式中上下文对象的通用能力
 *
 * @author bens
 */
public interface ChainContext {

    /**
     * 是否中断执行
     *
     * @return true=中断, false=继续
     */
    boolean isInterrupted();

    /**
     * 设置中断标识
     *
     * @param interrupted 中断标识
     */
    void setInterrupted(boolean interrupted);

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getErrorMessage();

    /**
     * 设置错误信息
     *
     * @param errorMessage 错误信息
     */
    void setErrorMessage(String errorMessage);

    /**
     * 中断执行并设置错误信息
     * 这是一个便捷方法，同时设置中断标识和错误信息
     *
     * @param errorMessage 错误信息
     */
    default void interrupt(String errorMessage) {
        setInterrupted(true);
        setErrorMessage(errorMessage);
    }

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    Long getTenantId();

    /**
     * 设置租户ID
     *
     * @param tenantId 租户ID
     */
    void setTenantId(Long tenantId);
}
