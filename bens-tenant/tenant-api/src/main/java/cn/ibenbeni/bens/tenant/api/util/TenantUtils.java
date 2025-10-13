package cn.ibenbeni.bens.tenant.api.util;

import cn.ibenbeni.bens.tenant.api.context.TenantContextHolder;

/**
 * 多租户工具类
 */
public class TenantUtils {

    /**
     * 指定租户执行任务
     *
     * @param tenantId 租户ID
     * @param runnable 任务
     */
    public static void execute(Long tenantId, Runnable runnable) {
        Long oldTenantId = TenantContextHolder.getTenantId();
        boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            TenantContextHolder.setTenantId(tenantId);
            TenantContextHolder.setIgnore(false); // 默认不忽略租户
            // 执行任务
            runnable.run();
        } finally {
            // 任务执行完毕，还原租户ID和是否忽略租户
            TenantContextHolder.setTenantId(oldTenantId);
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

    /**
     * 忽略租户执行任务
     *
     * @param runnable 任务
     */
    public static void executeIgnore(Runnable runnable) {
        Boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            TenantContextHolder.setIgnore(true);
            // 执行逻辑
            runnable.run();
        } finally {
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

}
