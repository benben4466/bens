package cn.ibenbeni.bens.tenant.db;

import cn.ibenbeni.bens.tenant.api.annotation.TenantIgnore;
import cn.ibenbeni.bens.tenant.api.context.TenantContextHolder;
import cn.ibenbeni.bens.tenant.api.prop.TenantProp;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.toolkit.SqlParserUtils;
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
public class TenantDbInterceptor implements TenantLineHandler {

    /**
     * 忽略表缓存
     */
    private final Map<String, Boolean> ignoreTables = new HashMap<>();

    public TenantDbInterceptor(TenantProp tenantProp) {
        tenantProp.getIgnoreTables().forEach(table -> addIgnoreTable(table, true));
    }

    @Override
    public Expression getTenantId() {
        return new LongValue(TenantContextHolder.getRequiredTenantId());
    }

    @Override
    public boolean ignoreTable(String tableName) {
        // 情况一：全局忽略多租户
        if (TenantContextHolder.isIgnore()) {
            return true;
        }

        // 情况二：忽略配置的表
        // removeWrapperSymbol:去掉SQL字段或表名外层的包裹符号
        tableName = SqlParserUtils.removeWrapperSymbol(tableName);
        Boolean ignore = ignoreTables.get(tableName);
        if (ignore == null) {
            ignore = computeIgnoreTable(tableName);
            synchronized (ignoreTables) {
                addIgnoreTable(tableName, ignore);
            }
        }

        return ignore;
    }

    /**
     * 计算表是否忽略租户条件
     * @param tableName 表明
     * @return
     */
    private boolean computeIgnoreTable(String tableName) {
        // 若无法从MyBatisPlus缓存中获取表信息，则认为该表不需要处理
        TableInfo tableInfo = TableInfoHelper.getTableInfo(tableName);
        if (tableInfo == null) {
            return true;
        }

        // 若DO实体添加了@TenantIgnore注解，则认为该表不需要处理
        TenantIgnore tenantIgnore = tableInfo.getEntityType().getAnnotation(TenantIgnore.class);
        return tenantIgnore != null;
    }

    private void addIgnoreTable(String tableName, boolean ignore) {
        ignoreTables.put(tableName.toLowerCase(), ignore);
        ignoreTables.put(tableName.toUpperCase(), ignore);
    }

}
