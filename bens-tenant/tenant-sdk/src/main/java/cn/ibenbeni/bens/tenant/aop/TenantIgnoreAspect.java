package cn.ibenbeni.bens.tenant.aop;

import cn.ibenbeni.bens.tenant.api.annotation.TenantIgnore;
import cn.ibenbeni.bens.tenant.api.context.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * {@link TenantIgnore}注解的切面类
 */
@Slf4j
@Aspect
public class TenantIgnoreAspect {

    @Pointcut("@annotation(tenantIgnore)")
    private void tenantIgnorePointcut(TenantIgnore tenantIgnore) {
    }

    @Around(value = "tenantIgnorePointcut(tenantIgnore)", argNames = "joinPoint,tenantIgnore")
    public Object around(ProceedingJoinPoint joinPoint, TenantIgnore tenantIgnore) throws Throwable {
        boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            boolean enable = Boolean.parseBoolean(tenantIgnore.enable());
            TenantContextHolder.setIgnore(enable);
            return joinPoint.proceed();
        } finally {
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

}
