package cn.ibenbeni.bens.message.center.common.chain;

/**
 * 责任链 Action 接口
 * 定义责任链模式中的行为节点
 *
 * @param <T> 上下文类型，必须实现 ChainContext 接口
 * @author bens
 */
public interface ChainAction<T extends ChainContext> {

    /**
     * 执行当前 Action 逻辑
     *
     * @param context 责任链上下文
     */
    void execute(T context);

    /**
     * 返回执行顺序
     * 数值越小越先执行
     *
     * @return 执行顺序
     */
    int getOrder();

}
