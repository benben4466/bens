package cn.ibenbeni.bens.ds.sdk;

import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 抽象动态获取数据源
 *
 * @author: benben
 * @time: 2025/6/25 下午3:08
 */
public abstract class AbstractRoutingDataSource extends AbstractDataSource {

    /**
     * 子类实现决定最终数据源
     *
     * @return 数据源
     */
    protected abstract DataSource determineDataSource();

    /**
     * 获取数据库连接
     *
     * @return 数据库连接对象
     */
    @Override
    public Connection getConnection() throws SQLException {
        return determineDataSource().getConnection();
    }

    /**
     * 获取数据库连接
     *
     * @param username 用户账号
     * @param password 用户密码
     * @return 数据库连接对象
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineDataSource().getConnection(username, password);
    }

    /**
     * 获取JDBC驱动原始类型实例
     *
     * @param iface 返回对象必须实现的Class
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return determineDataSource().unwrap(iface);
    }

    /**
     * 判断当前对象是否是指定接口的实现类
     *
     * @param iface 接口的Class
     */
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || determineDataSource().isWrapperFor(iface));
    }

}
