package cn.ibenbeni.bens.ds.sdk.aop;

import cn.ibenbeni.bens.ds.api.annotation.DataSource;
import cn.ibenbeni.bens.ds.api.constants.DatasourceContainerConstants;
import cn.ibenbeni.bens.ds.api.context.CurrentDataSourceContext;
import cn.ibenbeni.bens.rule.constants.ProjectAopSortConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

/**
 * 多数据源切换的AOP
 * <p>{@link DataSource}注解的AOP切面类</p>
 *
 * @author: benben
 * @time: 2025/6/25 下午2:06
 */
@Slf4j
@Aspect
public class MultiSourceExchangeAop implements Ordered {

    @Pointcut(value = "@annotation(cn.ibenbeni.bens.ds.api.annotation.DataSource)")
    private void cutService() {
    }

    /**
     * 多数据源环绕通知
     */
    @Around("cutService()")
    public Object aroundAdvice(ProceedingJoinPoint point) throws Throwable {
        DataSource datasource = getDataSource(point);
        if (datasource != null) {
            CurrentDataSourceContext.setDataSourceName(datasource.name());
        } else {
            // 默认使用主数据源
            CurrentDataSourceContext.setDataSourceName(DatasourceContainerConstants.MASTER_DATASOURCE_NAME);
        }
        log.debug("设置数据源为: {}", CurrentDataSourceContext.getDataSourceName());

        try {
            return point.proceed();
        } finally {
            log.debug("清空数据源信息, 数据源: {}", CurrentDataSourceContext.getDataSourceName());
            CurrentDataSourceContext.clearDataSourceName();
        }
    }

    /**
     * 获取数据源名称
     */
    private static DataSource getDataSource(ProceedingJoinPoint point) throws NoSuchMethodException {
        // 1.获取被拦截方法
        // 获取连接点签名，即被注解修饰的方法/类
        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        // 获取连接点的目标对象，即被代理的对象（实际对象，而不是代理对象）
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        // 2.获取方法上的DataSource注解
        return currentMethod.getAnnotation(DataSource.class);
    }

    /**
     * AOP的顺序要早于Spring的事务
     */
    @Override
    public int getOrder() {
        return ProjectAopSortConstants.MULTI_DATA_SOURCE_EXCHANGE_AOP;
    }

}
