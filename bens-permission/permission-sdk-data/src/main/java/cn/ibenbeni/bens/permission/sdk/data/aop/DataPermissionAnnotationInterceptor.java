package cn.ibenbeni.bens.permission.sdk.data.aop;

import cn.ibenbeni.bens.permission.sdk.data.annotation.DataPermission;
import cn.ibenbeni.bens.permission.sdk.data.context.DataPermissionContextHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * {@link DataPermission}注解的拦截器
 * 1.在执行方法前，将@DataPermission注解入栈
 * 2.在执行方法后，将@DataPermission注解出栈
 * <p>作用：记录注解的使用栈</p>
 */
public class DataPermissionAnnotationInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 尝试查找DataPermission注解
        DataPermission dataPermission = this.findAnnotation(invocation);
        // 不为空，则入栈
        if (dataPermission != null) {
            DataPermissionContextHolder.add(dataPermission);
        }
        try {
            return invocation.proceed();
        } finally {
            // 出栈
            if (dataPermission != null) {
                DataPermissionContextHolder.remove();
            }
        }
    }

    private DataPermission findAnnotation(MethodInvocation methodInvocation) {
        // 获取被拦截的方法对象
        Method method = methodInvocation.getMethod();
        // 获取被拦截方法的目标对象（即调用该方法的对象）
        Object targetObject = methodInvocation.getThis();
        Class<?> clazz = targetObject != null ? targetObject.getClass() : method.getDeclaringClass();

        // 从方法上获取
        DataPermission dataPermission = AnnotationUtils.findAnnotation(method, DataPermission.class);
        // 若为空，则尝试从类上获取
        if (dataPermission == null) {
            dataPermission = AnnotationUtils.findAnnotation(clazz, DataPermission.class);
        }

        return dataPermission;
    }

}
