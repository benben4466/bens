package cn.ibenbeni.bens.ds.api.sqladapter;

import cn.ibenbeni.bens.rule.enums.DbTypeEnum;

/**
 * 异构SQL获取基类
 * <p>通过继承此类，编写使用不同数据库的SQL</p>
 *
 * @author: benben
 * @time: 2025/6/25 下午8:37
 */
public abstract class AbstractSql {

    public String getSql(String jdbcUrl) {
        if (jdbcUrl.contains(DbTypeEnum.MYSQL.getUrlWords())) {
            return mysql();
        }
        if (jdbcUrl.contains(DbTypeEnum.PG_SQL.getUrlWords())) {
            return pgSql();
        }
        return mysql();
    }

    /**
     * 获取MySQL的SQL语句
     */
    protected abstract String mysql();

    /**
     * 获取PgSQL的SQL语句
     */
    protected abstract String pgSql();

}
