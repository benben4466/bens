package cn.ibenbeni.bens.permission.sdk.data.rule.dept;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.auth.api.LoginUserApi;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import cn.ibenbeni.bens.db.mp.util.MyBatisPlusUtils;
import cn.ibenbeni.bens.permission.api.exception.PermissionException;
import cn.ibenbeni.bens.permission.api.exception.enums.PermissionExceptionEnum;
import cn.ibenbeni.bens.permission.sdk.data.rule.DataPermissionRule;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.sys.api.PermissionApi;
import cn.ibenbeni.bens.sys.api.pojo.permission.dto.DeptDataPermissionRespDTO;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 部门数据权限规则实现
 * <p>注意：使用DeptDataPermissionRule时，需要确保表中有dept_id部门编号字段</p>
 * <p>
 * 实际业务场景：当用户修改部门时，冗余的dept_id部门编号是否需要修改？
 * 1.一般情况，dept_id不进行修改，则会导致用户看不到之前的数据。
 * 2.部分情况，希望该用户还是能看到之前的数据，则需要修改dept_id部门编号。有两种解决方案【需要改造DeptDataPermissionRule实现代码】
 * 1)编写洗数据的脚本，将dept_id部门编号修改为新部门编号。【建议】
 * 最终过滤条件是 WHERE dept_id = ?
 * 2）洗数据的话，可能涉及数据量较大，也可采用user_id进行过滤的方式，此时需要获取到dept_id对应的所有user_id用户编号
 * 最终过滤条件是 WHERE user_id IN (?, ?, ? ...)
 * 3)想要保证原dept_id和user_id都可以看的到，此时使用dept_id和user_id一起过滤
 * 最终过滤条件是 WHERE dept_id = ? OR user_id IN (?, ?, ? ...)
 * </p>
 */
@Slf4j
@AllArgsConstructor
public class DeptDataPermissionRule implements DataPermissionRule {

    /**
     * 部门ID字段默认名称
     */
    private static final String DEPT_COLUMN_NAME = "dept_id";

    /**
     * 空表达式
     */
    private static final Expression EXPRESSION_NULL = new NullValue();

    /**
     * 表名缓存集合
     */
    private final Set<String> TABLE_NAMES = new HashSet<>();

    /**
     * 基于部门的表配置
     * <p>Key=表名；Value=字段名称</p>
     * <p>一般情况下，每个表的部门字段是dept_id</p>
     */
    private final Map<String, String> deptColumns = new HashMap<>();

    private final LoginUserApi loginUserApi;

    private final PermissionApi permissionApi;

    @Override
    public Set<String> getTableNames() {
        return TABLE_NAMES;
    }

    @Override
    public Expression getExpression(String tableName, Alias tableAlias) {
        // 登陆情况下，才进行数据权限过滤
        LoginUser loginUser = loginUserApi.getLoginUser();
        if (loginUser == null) {
            return null;
        }

        // 获取数据权限
        DeptDataPermissionRespDTO deptDataPermission = permissionApi.getDeptDataPermission(loginUser.getUserId());
        if (deptDataPermission == null) {
            log.error("[getExpression][LoginUser({}) 获取权限数据为空]", JSON.toJSONString(loginUser));
            throw new PermissionException(PermissionExceptionEnum.PERMISSION_DATA_EMPTY, tableName, tableAlias);
        }

        // 根据数据权限，进行条件拼接
        // 情况一：全部数据
        if (deptDataPermission.getAll()) {
            return null;
        }

        // 情况二：不能查看指定部门 且 不能查看自己 则无数据权限
        if (CollUtil.isEmpty(deptDataPermission.getDeptIds()) && Boolean.FALSE.equals(deptDataPermission.getSelf())) {
            // Where null = null，可保证返回数据为空
            return new EqualsTo(null, null);
        }

        // 情况三：拼接dept_id条件
        Expression deptExpression = buildDeptExpression(tableName, tableAlias, deptDataPermission.getDeptIds());
        if (deptExpression == null) {
            return EXPRESSION_NULL;
        }

        return deptExpression;
    }

    /**
     * 构建拼接dept_id条件
     *
     * @param tableName  表名
     * @param tableAlias 表别名
     * @param deptIdSet  部门编号集合
     */
    private Expression buildDeptExpression(String tableName, Alias tableAlias, Set<Long> deptIdSet) {
        // 从缓存中获取表的部门ID字段名称
        String columnName = deptColumns.get(tableName);
        if (StrUtil.isBlank(columnName)) {
            return null;
        }
        // 部门ID集合则忽略
        if (CollUtil.isEmpty(deptIdSet)) {
            return null;
        }

        // 拼接条件
        // InExpression(Expression leftExpression, Expression rightExpression):等号左边和等号右边表达式
        return new InExpression(
                MyBatisPlusUtils.buildColumn(tableName, tableAlias, columnName),
                new ParenthesedExpressionList<>(new ExpressionList<LongValue>(CollectionUtils.convertList(deptIdSet, LongValue::new)))
        );
    }

    public void addDeptColumn(Class<? extends BaseEntity> entityClass) {
        addDeptColumn(entityClass, DEPT_COLUMN_NAME);
    }

    public void addDeptColumn(Class<? extends BaseEntity> entityClass, String columnName) {
        String tableName = TableInfoHelper.getTableInfo(entityClass).getTableName();
        addDeptColumn(tableName, columnName);
    }

    public void addDeptColumn(String tableName, String columnName) {
        deptColumns.put(tableName, columnName);
        TABLE_NAMES.add(tableName);
    }

}
