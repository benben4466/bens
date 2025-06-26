package cn.ibenbeni.bens.ds.sdk.context;


import cn.ibenbeni.bens.db.api.factory.DatasourceFactory;
import cn.ibenbeni.bens.db.api.pojo.db.DbProp;
import cn.ibenbeni.bens.ds.api.constants.DatasourceContainerConstants;
import cn.ibenbeni.bens.ds.sdk.persist.DataBaseInfoPersistence;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态数据源存储容器
 *
 * @author: benben
 * @time: 2025/6/25 下午3:03
 */
public class DataSourceContext {

    /**
     * 数据源容器
     * <p>key=数据源名称；value=数据源实例</p>
     */
    private static final Map<String, DataSource> DATA_SOURCES = new ConcurrentHashMap<>();

    /**
     * 数据源配置容器
     * <p>key=数据源名称；value=数据源配置实例</p>
     */
    private static Map<String, DbProp> DATA_SOURCES_CONF = new ConcurrentHashMap<>();

    /**
     * 初始化数据源
     *
     * @param dbProp     数据源配置实例
     * @param dataSource 数据源实例
     */
    public static void initDataSource(DbProp dbProp, DataSource dataSource) {
        // 清空数据库中的主数据源信息
        new DataBaseInfoPersistence(dbProp).deleteMasterDatabaseInfo();
        // 初始化主数据源信息
        new DataBaseInfoPersistence(dbProp).createMasterDatabaseInfo();

        // 从数据库中获取所有数据源信息
        DataBaseInfoPersistence dataBaseInfoDao = new DataBaseInfoPersistence(dbProp);
        Map<String, DbProp> allDataBaseInfo = dataBaseInfoDao.getAllDataBaseInfo();

        // 赋给全局变量
        DATA_SOURCES_CONF = allDataBaseInfo;

        // 根据数据源配置初始化数据源实例
        for (Map.Entry<String, DbProp> entry : allDataBaseInfo.entrySet()) {
            String dbName = entry.getKey();
            DbProp newDbProp = entry.getValue();

            // 若是主数据源，不用初始化第二次；否则初始化
            if (dbName.equalsIgnoreCase(DatasourceContainerConstants.MASTER_DATASOURCE_NAME)) {
                DATA_SOURCES_CONF.put(dbName, newDbProp);
                DATA_SOURCES.put(dbName, dataSource);
            } else {
                DataSource newDataSource = createDataSource(dbName, newDbProp);
                DATA_SOURCES.put(dbName, newDataSource);
            }
        }
    }

    /**
     * 获取所有数据源
     */
    public static Map<String, DataSource> getDataSources() {
        return DATA_SOURCES;
    }

    /**
     * 创建数据源
     *
     * @param dataSourceName 数据源名称
     * @param dbProp         数据源配置
     */
    private static DataSource createDataSource(String dataSourceName, DbProp dbProp) {
        DATA_SOURCES_CONF.put(dataSourceName, dbProp);
        return DatasourceFactory.createDatasource(dbProp);
    }

}
