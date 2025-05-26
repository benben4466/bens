package cn.ibenbeni.bens.rule.threadlocal;

/**
 * 对程序进行拓展，方便清除ThreadLocal
 *
 * @author benben
 */
public interface RemoveThreadLocalApi {

    /**
     * 具体删除ThreadLocal的逻辑
     */
    void removeThreadLocalAction();

}
