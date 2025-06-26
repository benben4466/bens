package cn.ibenbeni.bens.ds.sdk.persist.sqladapter;

import cn.ibenbeni.bens.ds.api.sqladapter.AbstractSql;

/**
 * 添加数据源的SQL
 *
 * @author: benben
 * @time: 2025/6/25 下午8:49
 */
public class AddDatabaseInfoSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "INSERT INTO `sys_database_info`(`db_id`, `db_name`, `jdbc_driver`, `jdbc_url`, `username`, `password`, `remarks`, `create_time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String pgSql() {
        return "INSERT INTO sys_database_info(db_id, db_name, jdbc_driver, jdbc_url, username, password,  remarks, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, to_timestamp(?,'YYYY-MM-DD HH24:MI:SS'))";
    }

}
