package cn.ibenbeni.bens.rule.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * 获取代理原始对象的工具
 *
 * @author: benben
 * @time: 2025/6/12 下午10:57
 */
@Slf4j
public class AopTargetUtils {

    public static Object getTarget(Object proxy) {
        // 不是代理对象，直接返回
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }

        // 判断是否是JDK还是Cglib代理的对象
        try {
            if (AopUtils.isJdkDynamicProxy(proxy)) {
                return getJdkDynamicProxyTargetObject(proxy);
            } else if (AopUtils.isCglibProxy(proxy)) {
                return getCglibProxyTargetObject(proxy);
            } else {
                log.error("请检查代理类型, 暂只支持JDK动态代理和Cglib动态代理");
                return null;
            }
        } catch (Exception ex) {
            log.error("获取代理对象异常", ex);
            return null;
        }
    }

    /**
     * 获取JDK代理的原始对象
     * <p>Spring AOP实现的JDK动态代理</p>
     *
     * @param proxy 代理对象
     */
    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        /*
         * 目标: 获取JDK动态代理的原始对象
         * 环境: Spring AOP实现的JDK动态代理类：JdkDynamicAopProxy
         * 步骤:
         *      1.从代理对象中获取JdkDynamicAopProxy对象。
         *      2.JdkDynamicAopProxy类属性类型为AdvisedSupport保持了目标对象、切面和增强等信息。
         *      3.AdvisedSupport类中属性类型为TargetSource保持了目标对象。
         * 问题:
         * 1.为什么从超类中获取属性名称为h的字段的值？
         *      代理对象肯定实现了InvocationHandler接口，此接口是负责拦截代理对象的方法调用。
         * 2.为什么获取到InvocationHandler实例后，继而获取advised字段的值？
         *      Spring AOP的JDK动态代理对象实现类是JdkDynamicAopProxy，继承了Proxy类，继承了InvocationHandler接口。
         *      JdkDynamicAopProxy类中属性类型为AdvisedSupport，此属性名称为advised。AdvisedSupport类存储目标对象、切面和增强等信息。
         *      在AdvisedSupport类属性类型为TargetSource保存了原始对象。
         */

        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
    }

    /**
     * 获取Cglib代理的对象
     *
     * @param proxy 代理对象
     */
    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        // Cglib默认将CGLIB$CALLBACK_0用于存储目标类拦截器，通过此属性获取，目标类
        // CGLIB$CALLBACK_1、CGLIB$CALLBACK_2等存储拦截器或增强器
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object invocationHandler = h.get(proxy);
        Field advised = invocationHandler.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return ((AdvisedSupport) advised.get(invocationHandler)).getTargetSource().getTarget();
    }

}
