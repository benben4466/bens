package cn.ibenbeni.bens.db.mp.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * MyBatisPlus工具类
 *
 * @author: benben
 * @time: 2025/6/27 下午2:55
 */
public class MyBatisPlusUtils {

    private static final String MYSQL_ESCAPE_CHARACTER = "`";

    /**
     * 将拦截器添加到指定位置
     *
     * @param interceptor 拦截器管理类
     * @param inner       拦截器
     * @param index       位置
     */
    public static void addInterceptor(MybatisPlusInterceptor interceptor, InnerInterceptor inner, int index) {
        List<InnerInterceptor> inners = new ArrayList<>(interceptor.getInterceptors());
        inners.add(index, inner);
        interceptor.setInterceptors(inners);
    }

    /**
     * 获取表名
     * <p>
     * MySQL表名可能带``
     * </p>
     *
     * @param table 表对象
     * @return 去除转移字符后的表名
     */
    public static String getTableName(Table table) {
        String tableName = table.getName();
        // MySQL表名可能带``
        if (tableName.startsWith(MYSQL_ESCAPE_CHARACTER) && tableName.endsWith(MYSQL_ESCAPE_CHARACTER)) {
            tableName = tableName.substring(1, tableName.length() - 1);
        }
        return tableName;
    }

    /**
     * 构建Column对象
     *
     * @param tableName  表名
     * @param tableAlias 表别名
     * @param columnName 列名
     * @return Column对象
     */
    public static Column buildColumn(String tableName, Alias tableAlias, String columnName) {
        if (tableAlias != null) {
            tableName = tableAlias.getName();
        }
        return new Column(tableName + StringPool.DOT + columnName);
    }

}
