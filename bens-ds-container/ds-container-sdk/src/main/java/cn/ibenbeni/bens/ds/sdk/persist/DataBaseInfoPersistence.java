package cn.ibenbeni.bens.ds.sdk.persist;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.db.api.pojo.db.DbProp;
import cn.ibenbeni.bens.ds.api.constants.DatasourceContainerConstants;
import cn.ibenbeni.bens.ds.api.exception.DatasourceContainerException;
import cn.ibenbeni.bens.ds.api.exception.enums.DatasourceContainerExceptionEnum;
import cn.ibenbeni.bens.ds.sdk.persist.sqladapter.AddDatabaseInfoSql;
import cn.ibenbeni.bens.ds.sdk.persist.sqladapter.DatabaseListSql;
import cn.ibenbeni.bens.ds.sdk.persist.sqladapter.DeleteDatabaseInfoSql;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源信息(sys_database_info)相关操作的DAO
 *
 * @author: benben
 * @time: 2025/6/25 下午8:32
 */
@Slf4j
@RequiredArgsConstructor
public class DataBaseInfoPersistence {

    private final DbProp dbProp;

    /**
     * 初始化master的数据源，要和DbProp配置的数据源一致
     */
    public void createMasterDatabaseInfo() {
        Connection conn = null;
        try {
            Class.forName(dbProp.getDriverClassName());
            conn = DriverManager.getConnection(dbProp.getUrl(), dbProp.getUsername(), dbProp.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement(new AddDatabaseInfoSql().getSql(dbProp.getUrl()));

            // 设置记录属性
            preparedStatement.setLong(1, IdWorker.getId());
            preparedStatement.setString(2, DatasourceContainerConstants.MASTER_DATASOURCE_NAME);
            preparedStatement.setString(3, dbProp.getDriverClassName());
            preparedStatement.setString(4, dbProp.getUrl());
            preparedStatement.setString(5, dbProp.getUsername());
            preparedStatement.setString(6, dbProp.getPassword());
            preparedStatement.setString(7, "主数据源, 项目启动数据源");
            preparedStatement.setString(8, DateUtil.formatDateTime(new Date()));

            int saveNum = preparedStatement.executeUpdate();
            log.info("初始化master的databaseInfo信息！初始化" + saveNum + "条");
        } catch (Exception ex) {
            log.error("初始化master的databaseInfo信息错误！", ex);
            String userTip = StrUtil.format(DatasourceContainerExceptionEnum.INSERT_DBS_DAO_ERROR.getUserTip(), ex.getMessage());
            throw new DatasourceContainerException(DatasourceContainerExceptionEnum.INSERT_DBS_DAO_ERROR, userTip);
        } finally {
            IoUtil.close(conn);
        }
    }

    /**
     * 清空master数据源信息
     */
    public void deleteMasterDatabaseInfo() {
        Connection conn = null;
        try {
            // 加载驱动
            Class.forName(dbProp.getDriverClassName());
            conn = DriverManager.getConnection(dbProp.getUrl(), dbProp.getUsername(), dbProp.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement(new DeleteDatabaseInfoSql().getSql(dbProp.getUrl()));
            preparedStatement.setString(1, DatasourceContainerConstants.MASTER_DATASOURCE_NAME);
            int deleteNum = preparedStatement.executeUpdate();
            log.info("删除master的databaseInfo信息！删除" + deleteNum + "条");
        } catch (Exception ex) {
            log.info("删除master的databaseInfo信息失败", ex);
            String userTip = StrUtil.format(DatasourceContainerExceptionEnum.DELETE_DBS_DAO_ERROR.getUserTip(), ex.getMessage());
            throw new DatasourceContainerException(DatasourceContainerExceptionEnum.DELETE_DBS_DAO_ERROR, userTip);
        } finally {
            IoUtil.close(conn);
        }
    }

    public Map<String, DbProp> getAllDataBaseInfo() {
        Map<String, DbProp> dataSourceList = new HashMap<>(16);
        Connection conn = null;
        try {
            Class.forName(dbProp.getDriverClassName());
            conn = DriverManager.getConnection(dbProp.getUrl(), dbProp.getUsername(), dbProp.getPassword());
            PreparedStatement preparedStatement = conn.prepareStatement(new DatabaseListSql().getSql(dbProp.getUrl()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DbProp newDbProp = this.createDbProp(resultSet);
                String dbName = resultSet.getString("db_name");
                dataSourceList.put(dbName, newDbProp);
            }

            return dataSourceList;
        } catch (Exception ex) {
            log.error("查询数据源信息错误！", ex);
            String userTip = StrUtil.format(DatasourceContainerExceptionEnum.QUERY_DBS_DAO_ERROR.getUserTip(), ex.getMessage());
            throw new DatasourceContainerException(DatasourceContainerExceptionEnum.QUERY_DBS_DAO_ERROR, userTip);
        } finally {
            IoUtil.close(conn);
        }
    }

    /**
     * 通过查询结果组装DbProp
     */
    private DbProp createDbProp(ResultSet resultSet) {
        DbProp newDbProp = new DbProp();
        BeanUtil.copyProperties(this.dbProp, newDbProp, CopyOptions.create().ignoreError());

        try {
            newDbProp.setDriverClassName(resultSet.getString("jdbc_driver"));
            newDbProp.setUrl(resultSet.getString("jdbc_url"));
            newDbProp.setUsername(resultSet.getString("username"));
            newDbProp.setPassword(resultSet.getString("password"));
        } catch (Exception ex) {
            log.info("根据数据库查询结果，创建DruidProperties失败", ex);
            String userTip = StrUtil.format(DatasourceContainerExceptionEnum.CREATE_PROP_DAO_ERROR.getUserTip(), ex.getMessage());
            throw new DatasourceContainerException(DatasourceContainerExceptionEnum.CREATE_PROP_DAO_ERROR, userTip);
        }

        return newDbProp;
    }

}
