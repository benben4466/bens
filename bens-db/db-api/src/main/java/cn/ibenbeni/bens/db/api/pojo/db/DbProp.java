package cn.ibenbeni.bens.db.api.pojo.db;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据库数据源配置
 * <p>说明：此处仅为存储"application.yml"文件中配置</p>
 *
 * @author: benben
 * @time: 2025/6/16 下午10:32
 */
@Data
@Slf4j
public class DbProp {

    /**
     * 数据源名称
     */
    private String dataSourceName;

    /**
     * 数据库驱动名称
     */
    private String driverClassName;

    /**
     * 连接数据库的URL
     * <p>如MySQL: jdbc:mysql://127.0.0.1:3306/bens</p>
     */
    private String url;

    /**
     * 连接数据库的用户名
     */
    private String username;

    /**
     * 连接数据库的密码
     */
    private String password;

    /**
     * 连接池初始化大小
     * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
     */
    private Integer initialSize = 2;

    /**
     * 最大连接池数量
     */
    private Integer maxActive = 20;

    /**
     * 最小连接池数量
     */
    private Integer minIdle = 1;

    /**
     * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
     */
    private Integer maxWait = 60000;

}
