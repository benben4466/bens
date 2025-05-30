package cn.ibenbeni.bens.rule.callback;

/**
 * 删除回调方法
 *
 * @author: benben
 * @time: 2025/5/30 上午11:07
 */
public interface RemoveCallbackApi {

    /**
     * 校验被删除信息，是否有业务关联关系
     * <p>
     * 如果有绑定关系直接抛出异常即可
     *
     * @param args 参数
     */
    void validateHaveBind(Object args);

    /**
     * 删除信息后的回调
     *
     * @param args 参数
     */
    void removeAction(Object args);

}
