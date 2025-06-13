package cn.ibenbeni.bens.event.sdk.container;

import cn.ibenbeni.bens.event.sdk.pojo.BusinessListenerDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件容器
 *
 * @author: benben
 * @time: 2025/6/12 下午10:29
 */
public class EventContainer {

    /**
     * 事件消费者容器
     * <p>key=业务编号；Value=业务编号对应的所有消费者</p>
     */
    private static final Map<String, List<BusinessListenerDetail>> CONTEXT = new HashMap<>();

    /**
     * 获取业务编码对应的所有消费者
     */
    public static List<BusinessListenerDetail> getListener(String businessCode) {
        return CONTEXT.get(businessCode);
    }

    /**
     * 根据业务编码添加业务消费者
     */
    public static void addListener(String businessCode, BusinessListenerDetail businessListenerItem) {
        List<BusinessListenerDetail> listener = CONTEXT.get(businessCode);
        if (listener == null) {
            listener = new ArrayList<>();
        }
        listener.add(businessListenerItem);
        CONTEXT.put(businessCode, listener);
    }

    /**
     * 根据业务编码添加业务消费者
     */
    public static void addListenerList(String businessCode, List<BusinessListenerDetail> businessListenerItemList) {
        List<BusinessListenerDetail> listener = CONTEXT.get(businessCode);
        if (listener == null) {
            listener = new ArrayList<>();
        }
        listener.addAll(businessListenerItemList);
        CONTEXT.put(businessCode, listener);
    }

}
