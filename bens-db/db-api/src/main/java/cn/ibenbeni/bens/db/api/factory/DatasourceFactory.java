package cn.ibenbeni.bens.db.api.factory;

import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.db.api.pojo.db.DbProp;
import cn.ibenbeni.bens.rule.enums.DbTypeEnum;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

/**
 * 数据源工厂类
 *
 * @author: benben
 * @time: 2025/6/25 下午4:59
 */
@Slf4j
public class DatasourceFactory {

    /**
     * 获取数据源
     *
     * @param dbProp 数据库配置
     */
    public static DataSource createDatasource(DbProp dbProp) {
        // 设置数据库连接信息
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(dbProp.getDriverClassName());
        hikariConfig.setJdbcUrl(dbProp.getUrl());
        hikariConfig.setUsername(dbProp.getUsername());
        hikariConfig.setPassword(dbProp.getPassword());
        hikariConfig.setMaximumPoolSize(dbProp.getMaxActive());
        hikariConfig.setMinimumIdle(dbProp.getMinIdle());
        hikariConfig.setConnectionTimeout(dbProp.getMaxWait());
        hikariConfig.addDataSourceProperty("cachePrepStmts", dbProp.getPoolPreparedStatements());
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", dbProp.getMaxPoolPreparedStatementPerConnectionSize());
        hikariConfig.setConnectionTestQuery(getConnectionTestQueryByUrl(dbProp.getUrl()));
        hikariConfig.setValidationTimeout(dbProp.getValidationQueryTimeout() * 1000);
        if (ObjectUtil.isNotEmpty(dbProp.getKeepAlive()) && dbProp.getKeepAlive()) {
            if (dbProp.getKeepAlive() == false) {
                hikariConfig.setKeepaliveTime(0);
            } else {
                hikariConfig.setKeepaliveTime(dbProp.getTimeBetweenEvictionRunsMillis());
            }
        }

        return new HikariDataSource(hikariConfig);
    }

    /**
     * 获取数据库连接测试查询语句
     */
    private static String getConnectionTestQueryByUrl(String url) {
        for (DbTypeEnum value : DbTypeEnum.values()) {
            if (url.contains(value.getUrlWords())) {
                return value.getConnectionTestQuery();
            }
        }

        // 默认MySQL数据库
        return DbTypeEnum.MYSQL.getConnectionTestQuery();
    }

}
