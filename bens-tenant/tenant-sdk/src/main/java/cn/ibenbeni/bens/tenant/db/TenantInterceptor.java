package cn.ibenbeni.bens.tenant.db;

import cn.ibenbeni.bens.tenant.api.context.TenantContextHolder;
import cn.ibenbeni.bens.tenant.api.prop.TenantProp;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import java.util.HashMap;
import java.util.Map;

/**
 * DB层多租户实现
 * <p>基于MyBatis-Plus框架的多租户插件</p>
 *
 * @author: benben
 * @time: 2025/6/27 下午2:02
 */
public class TenantInterceptor implements TenantLineHandler { // TenantMetaObjectHandler

    /**
     * 忽略表缓存
     */
    private final Map<String, Boolean> ignoreTables = new HashMap<>();

    public TenantInterceptor(TenantProp tenantProp) {
        tenantProp.getIgnoreTables().forEach(table -> ignoreTables.put(table, true));
    }

    @Override
    public Expression getTenantId() {
        return new LongValue(TenantContextHolder.getRequiredTenantId());
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return Boolean.TRUE.equals(ignoreTables.get(tableName));
    }

}
