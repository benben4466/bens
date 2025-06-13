package cn.ibenbeni.bens.event.sdk.publish;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.event.api.exception.enums.EventExceptionEnum;
import cn.ibenbeni.bens.event.sdk.container.EventContainer;
import cn.ibenbeni.bens.event.sdk.pojo.BusinessListenerDetail;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 业务事件发布者
 *
 * @author: benben
 * @time: 2025/6/13 下午4:48
 */
@Slf4j
public class BusinessEventPublisher {

    /**
     * 发布业务事件
     * <p>发布@BusinessListener注解相关的事件</p>
     *
     * @param businessCode   业务代码
     * @param businessObject 业务对象
     * @param <T>            业务对象类型
     */
    public static <T> void publishEvent(String businessCode, T businessObject) {
        // 获取业务编码对应的监听器
        List<BusinessListenerDetail> listeners = EventContainer.getListener(businessCode);
        if (CollUtil.isEmpty(listeners)) {
            log.debug("业务编码: {} 的监听器为空, 丢弃该消息. 消息: {}", businessCode, JSON.toJSON(businessObject));
            return;
        }

        // 依次调用监听器, 处理业务事件
        for (BusinessListenerDetail listener : listeners) {
            String beanName = listener.getBeanName();
            Object bean = SpringUtil.getBean(beanName);

            Method listenerMethod = listener.getConsumerMethod();
            Class<?> paramClassType = listener.getParamClassType();

            // 校验参数与监听器详情是否匹配，若不匹配则忽略
            if (!validateParam(businessObject, listener)) {
                log.error("业务事件参数校验失败, 业务编码: {} - Bean名称: {} - 方法名称: {} - 业务对象: {} - 参数类型: {}", businessCode, beanName, listenerMethod.getName(), businessObject, paramClassType);
                return;
            }

            if (ObjectUtil.isEmpty(paramClassType)) {
                try {
                    listenerMethod.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    log.error("方法调用失败，反射调用异常", ex);
                    throw new ServiceException(EventExceptionEnum.ERROR_INVOKE);
                }
            } else {
                try {
                    listenerMethod.invoke(bean, businessObject);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    log.error("方法调用失败，反射调用异常", ex);
                    throw new ServiceException(EventExceptionEnum.ERROR_INVOKE);
                }
            }
        }
    }

    /**
     * 验证参数
     *
     * @param businessObject 参数
     * @return true=验证通过；false=验证失败
     */
    private static boolean validateParam(Object businessObject, BusinessListenerDetail businessListener) {
        if (ObjectUtil.isEmpty(businessObject)) {
            return ObjectUtil.isEmpty(businessListener.getParamClassType());
        } else {
            if (ObjectUtil.isNotEmpty(businessListener.getParamClassType())) {
                return businessListener.getParamClassType().equals(businessObject.getClass())
                        || businessListener.getParamClassType().isAssignableFrom(businessObject.getClass());
            }
        }

        return false;
    }

}
