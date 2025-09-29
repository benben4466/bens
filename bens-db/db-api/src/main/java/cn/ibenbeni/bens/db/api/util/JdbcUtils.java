package cn.ibenbeni.bens.db.api.util;

import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.db.api.enums.DbTypeEnum;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC 工具类
 */
public class JdbcUtils {

    public static DbType getDbType() {
        DataSource dataSource;
        try {
            DynamicRoutingDataSource dynamicRoutingDataSource = SpringUtil.getBean(DynamicRoutingDataSource.class);
            dataSource = dynamicRoutingDataSource.determineDataSource();
        } catch (NoSuchBeanDefinitionException ex) {
            dataSource = SpringUtil.getBean(DataSource.class);
        }
        try {
            Connection conn = dataSource.getConnection();
            return DbTypeEnum.find(conn.getMetaData().getDatabaseProductName());
        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

}
