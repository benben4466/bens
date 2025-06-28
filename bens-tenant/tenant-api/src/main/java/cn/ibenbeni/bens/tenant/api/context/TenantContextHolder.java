package cn.ibenbeni.bens.tenant.api.context;

import cn.ibenbeni.bens.tenant.api.exception.TenantException;
import cn.ibenbeni.bens.tenant.api.exception.enums.TenantExceptionEnum;

/**
 * 多租户上下文 Holder
 *
 * @author: benben
 * @time: 2025/6/27 上午11:30
 */
public class TenantContextHolder {

    /**
     * 当前租户编号
     */
    private static final ThreadLocal<Long> TENANT_ID = new ThreadLocal<>();

    /**
     * 是否忽略租户
     */
    private static final ThreadLocal<Boolean> IGNORE = new ThreadLocal<>();

    /**
     * 获取获取当前租户编号，如果为空则抛出异常
     */
    public static Long getRequiredTenantId() {
        Long tenantId = getTenantId();
        if (tenantId == null) {
            throw new TenantException(TenantExceptionEnum.TENANT_REQUEST_NOT_EXIST);
        }
        return tenantId;
    }

    public static Long getTenantId() {
        return TENANT_ID.get();
    }

    public static void setTenantId(Long tenantId) {
        TENANT_ID.set(tenantId);
    }

    /**
     * 是否忽略租户
     */
    public static boolean isIgnore() {
        return Boolean.TRUE.equals(IGNORE.get());
    }

    public static void setIgnore(boolean ignore) {
        IGNORE.set(ignore);
    }

    public static void clear() {
        TENANT_ID.remove();
        IGNORE.remove();
    }

}
