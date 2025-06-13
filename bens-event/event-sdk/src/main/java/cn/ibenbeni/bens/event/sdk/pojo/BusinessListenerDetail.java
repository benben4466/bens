package cn.ibenbeni.bens.event.sdk.pojo;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 业务事件消费者
 *
 * @author: benben
 * @time: 2025/6/12 下午10:20
 */
@Data
public class BusinessListenerDetail {

    /**
     * Spring的Bean名称
     */
    private String beanName;

    /**
     * 消费者方法
     */
    private Method consumerMethod;

    /**
     * 消费者方法的参数类型
     * <p>如果方法有多个参数，这里只获取第一个参数的类型，如果方法没参数，则这里为null</p>
     * <p>@BusinessListener注解应用的方法都是一个参数</p>
     */
    private Class<?> paramClassType;

}
