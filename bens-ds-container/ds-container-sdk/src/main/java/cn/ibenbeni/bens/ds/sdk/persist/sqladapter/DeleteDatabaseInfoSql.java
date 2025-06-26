package cn.ibenbeni.bens.ds.sdk.persist.sqladapter;

import cn.ibenbeni.bens.ds.api.sqladapter.AbstractSql;

/**
 * 删除数据源SQL
 *
 * @author: benben
 * @time: 2025/6/25 下午8:36
 */
public class DeleteDatabaseInfoSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "DELETE from sys_database_info where db_name = ?";
    }

    @Override
    protected String pgSql() {
        return "DELETE from sys_database_info where db_name = ?";
    }

}
