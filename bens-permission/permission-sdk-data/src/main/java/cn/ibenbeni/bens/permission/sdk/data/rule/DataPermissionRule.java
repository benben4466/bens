package cn.ibenbeni.bens.permission.sdk.data.rule;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;

import java.util.Set;

/**
 * 数据权限规则
 * <p>通过实现接口，自定义数据规则</p>
 */
public interface DataPermissionRule {

    /**
     * 返回规则生效的表名数组
     */
    Set<String> getTableNames();

    /**
     * 根据表名和别名，生成数据权限的Where条件
     *
     * @param tableName  表名
     * @param tableAlias 表别名
     * @return 数据权限的Where条件
     */
    Expression getExpression(String tableName, Alias tableAlias);

}
