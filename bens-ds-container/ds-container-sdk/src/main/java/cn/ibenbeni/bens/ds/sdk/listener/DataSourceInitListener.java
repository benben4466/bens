package cn.ibenbeni.bens.ds.sdk.listener;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.db.api.factory.DatasourceFactory;
import cn.ibenbeni.bens.db.api.pojo.db.DbProp;
import cn.ibenbeni.bens.ds.api.exception.DatasourceContainerException;
import cn.ibenbeni.bens.ds.api.exception.enums.DatasourceContainerExceptionEnum;
import cn.ibenbeni.bens.ds.sdk.context.DataSourceContext;
import cn.ibenbeni.bens.rule.listener.ContextInitializedListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.sql.DataSource;

/**
 * @author: benben
 * @time: 2025/6/25 下午4:37
 */
@Slf4j
public class DataSourceInitListener extends ContextInitializedListener implements Ordered {

    @Override
    public void eventCallback(ApplicationContextInitializedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();

        // 获取数据库配置实例
        DbProp dbProp = this.getDbProp(environment);
        // 创建数据源
        DataSource datasource = DatasourceFactory.createDatasource(dbProp);

        try {
            // 初始化数据源容器
            DataSourceContext.initDataSource(dbProp, datasource);
        } catch (Exception ex) {
            log.error("初始化数据源容器错误", ex);
            throw new DatasourceContainerException(DatasourceContainerExceptionEnum.INIT_DATASOURCE_CONTAINER_ERROR, ex.getMessage());
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 200;
    }

    /**
     * 获取数据库配置实例
     *
     * @param environment 程序配置信息
     */
    private DbProp getDbProp(ConfigurableEnvironment environment) {
        DbProp dbProp = new DbProp();

        // 获取数据库连接配置
        String dataSourceDriver = environment.getProperty("spring.datasource.driver-class-name");
        String dataSourceUrl = environment.getProperty("spring.datasource.url");
        String dataSourceUsername = environment.getProperty("spring.datasource.username");
        String dataSourcePassword = environment.getProperty("spring.datasource.password");
        String initialSize = environment.getProperty("spring.datasource.initialSize");
        String maxActive = environment.getProperty("spring.datasource.maxActive");
        String minIdle = environment.getProperty("spring.datasource.minIdle");
        String maxWait = environment.getProperty("spring.datasource.maxWait");
        String poolPreparedStatements = environment.getProperty("spring.datasource.poolPreparedStatements");
        String maxPoolPreparedStatementPerConnectionSize = environment.getProperty("spring.datasource.maxPoolPreparedStatementPerConnectionSize");
        String validationQuery = environment.getProperty("spring.datasource.validationQuery");
        String validationQueryTimeout = environment.getProperty("spring.datasource.validationQueryTimeout");
        String testOnBorrow = environment.getProperty("spring.datasource.testOnBorrow");
        String testOnReturn = environment.getProperty("spring.datasource.testOnReturn");
        String testWhileIdle = environment.getProperty("spring.datasource.testWhileIdle");
        String keepAlive = environment.getProperty("spring.datasource.keepAlive");
        String timeBetweenEvictionRunsMillis = environment.getProperty("spring.datasource.timeBetweenEvictionRunsMillis");
        String minEvictableIdleTimeMillis = environment.getProperty("spring.datasource.minEvictableIdleTimeMillis");
        String filters = environment.getProperty("spring.datasource.filters");

        // 数据源重要连接信息不能为空
        if (StrUtil.hasBlank(dataSourceDriver, dataSourceUrl, dataSourceUsername, dataSourcePassword)) {
            String userTip = StrUtil.format(DatasourceContainerExceptionEnum.DB_CONNECTION_INFO_EMPTY_ERROR.getUserTip(), dataSourceUrl, dataSourceUsername);
            throw new DatasourceContainerException(DatasourceContainerExceptionEnum.DB_CONNECTION_INFO_EMPTY_ERROR, userTip);
        }

        // 创建数据源配置实体
        dbProp.setDriverClassName(dataSourceDriver);
        dbProp.setUrl(dataSourceUrl);
        dbProp.setUsername(dataSourceUsername);
        dbProp.setPassword(dataSourcePassword);
        if (StrUtil.isNumeric(initialSize)) {
            dbProp.setInitialSize(Convert.toInt(initialSize));
        }
        if (StrUtil.isNumeric(maxActive)) {
            dbProp.setMaxActive(Convert.toInt(maxActive));
        }
        if (StrUtil.isNumeric(minIdle)) {
            dbProp.setMinIdle(Convert.toInt(minIdle));
        }
        if (StrUtil.isNumeric(maxWait)) {
            dbProp.setMaxWait(Convert.toInt(maxWait));
        }
        if (ObjectUtil.isNotEmpty(poolPreparedStatements)) {
            dbProp.setPoolPreparedStatements(Convert.toBool(poolPreparedStatements));
        }
        if (StrUtil.isNumeric(maxPoolPreparedStatementPerConnectionSize)) {
            dbProp.setMaxPoolPreparedStatementPerConnectionSize(Convert.toInt(maxPoolPreparedStatementPerConnectionSize));
        }
        if (ObjectUtil.isNotEmpty(validationQuery)) {
            dbProp.setValidationQuery(validationQuery);
        }
        if (StrUtil.isNumeric(validationQueryTimeout)) {
            dbProp.setValidationQueryTimeout(Convert.toInt(validationQueryTimeout));
        }
        if (ObjectUtil.isNotEmpty(testOnBorrow)) {
            dbProp.setTestOnBorrow(Convert.toBool(testOnBorrow));
        }
        if (ObjectUtil.isNotEmpty(testOnReturn)) {
            dbProp.setTestOnReturn(Convert.toBool(testOnReturn));
        }
        if (ObjectUtil.isNotEmpty(testWhileIdle)) {
            dbProp.setTestWhileIdle(Convert.toBool(testWhileIdle));
        }
        if (ObjectUtil.isNotEmpty(keepAlive)) {
            dbProp.setKeepAlive(Convert.toBool(keepAlive));
        }
        if (StrUtil.isNumeric(timeBetweenEvictionRunsMillis)) {
            dbProp.setTimeBetweenEvictionRunsMillis(Convert.toInt(timeBetweenEvictionRunsMillis));
        }
        if (StrUtil.isNumeric(minEvictableIdleTimeMillis)) {
            dbProp.setMinEvictableIdleTimeMillis(Convert.toInt(minEvictableIdleTimeMillis));
        }
        if (ObjectUtil.isNotEmpty(filters)) {
            dbProp.setFilters(filters);
        }

        return dbProp;
    }

}
