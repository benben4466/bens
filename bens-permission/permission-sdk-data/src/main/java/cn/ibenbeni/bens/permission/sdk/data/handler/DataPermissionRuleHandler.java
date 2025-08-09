package cn.ibenbeni.bens.permission.sdk.data.handler;

import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.db.mp.util.MyBatisPlusUtils;
import cn.ibenbeni.bens.permission.sdk.data.helper.DataPermissionRuleHelper;
import cn.ibenbeni.bens.permission.sdk.data.rule.DataPermissionRule;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.schema.Table;

import java.util.List;

/**
 * 数据权限处理处理器
 * <p>基于MyBatis-Plus的{@link MultiDataPermissionHandler}数据权限插件</p>
 * <p>核心原理：它会在SQL执行前拦截SQL语句，并根据用户数据权限动态添加SQL片段</p>
 */
@RequiredArgsConstructor
public class DataPermissionRuleHandler implements MultiDataPermissionHandler {

    private final DataPermissionRuleHelper ruleHelper;

    /**
     * 获取数据权限 SQL 片段
     * <p>
     * 作用：
     * 1.获取数据权限规则
     * 2.根据数据权限规则生成Expression
     * 3.返回Expression
     * </p>
     *
     * @param table             所执行的数据库表信息，可以通过此参数获取表名和表别名
     * @param where             原有的 where 条件信息
     * @param mappedStatementId Mybatis MappedStatement Id 根据该参数可以判断具体执行方法
     */
    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {

        // 获取Mapper对应的数据权限规则
        List<DataPermissionRule> rules = ruleHelper.getDataPermissionRule(mappedStatementId);
        if (CollUtil.isEmpty(rules)) {
            return null;
        }

        // 生成Where条件
        Expression allExpression = null;
        for (DataPermissionRule rule : rules) {
            String tableName = MyBatisPlusUtils.getTableName(table);
            // 判断是否忽略当前表
            if (!rule.getTableNames().contains(tableName)) {
                continue;
            }

            Expression oneExpress = rule.getExpression(tableName, table.getAlias());
            if (oneExpress == null) {
                continue;
            }
            // 拼接到allExpression中
            allExpression = allExpression == null ? oneExpress : new AndExpression(allExpression, oneExpress);
        }

        return allExpression;
    }

}
