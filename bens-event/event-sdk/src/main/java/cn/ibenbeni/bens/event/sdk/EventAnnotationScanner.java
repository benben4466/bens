package cn.ibenbeni.bens.event.sdk;

import cn.ibenbeni.bens.event.api.annotation.BusinessListener;
import cn.ibenbeni.bens.event.sdk.container.EventContainer;
import cn.ibenbeni.bens.event.sdk.pojo.BusinessListenerDetail;
import cn.ibenbeni.bens.rule.util.AopTargetUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * 事务扫描器
 * <p>将业务消费者，</p>
 *
 * @author: benben
 * @time: 2025/6/12 下午10:53
 */
@Slf4j
public class EventAnnotationScanner implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // 获取原始对象
        Object aopTarget = AopTargetUtils.getTarget(bean);
        if (aopTarget == null) {
            aopTarget = bean;
        }

        // 判断类中是否有@BusinessListener注解
        this.doScan(aopTarget.getClass(), beanName);
        return bean;
    }

    /**
     * 扫描整个类中包含的所有@BusinessListener注解
     */
    private void doScan(Class<?> clazz, String beanName) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            BusinessListener businessListener = method.getAnnotation(BusinessListener.class);
            log.debug("扫描到业务事件监听器: {}", businessListener);
            if (businessListener == null) {
                continue;
            }
            saveToEventContainer(beanName, method, businessListener);
        }
    }

    /**
     * 将业务监听器保持至业务事件容器中
     *
     * @param beanName         Bean名称
     * @param method           被监听器注解的方法对象
     * @param businessListener 监听器
     */
    private void saveToEventContainer(String beanName, Method method, BusinessListener businessListener) {
        String businessCode = businessListener.businessCode();

        // 初始化监听器详情
        BusinessListenerDetail businessListenerDetail = new BusinessListenerDetail();
        businessListenerDetail.setBeanName(beanName);
        businessListenerDetail.setConsumerMethod(method);
        if (method.getParameterCount() > 0) {
            businessListenerDetail.setParamClassType(method.getParameterTypes()[0]);
        }

        // 存储到事件容器中
        EventContainer.addListener(businessCode, businessListenerDetail);
    }

}
