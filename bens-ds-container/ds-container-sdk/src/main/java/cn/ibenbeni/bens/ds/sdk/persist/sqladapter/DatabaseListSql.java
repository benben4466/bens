package cn.ibenbeni.bens.ds.sdk.persist.sqladapter;

import cn.ibenbeni.bens.ds.api.sqladapter.AbstractSql;

/**
 * 数据源列表SQL
 *
 * @author: benben
 * @time: 2025/6/25 下午8:54
 */
public class DatabaseListSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "select db_name, jdbc_driver, jdbc_url, username, password from sys_database_info where del_flag = 'N'";
    }

    @Override
    protected String pgSql() {
        return "select db_name,jdbc_driver,jdbc_url,username,password from sys_database_info";
    }

}
