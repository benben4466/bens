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

    /**
     * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
     */
    private Boolean poolPreparedStatements = true;

    /**
     * 要启用PSCache，必须配置大于0，可以配置-1关闭
     * 当大于0时，poolPreparedStatements自动触发修改为true。
     */
    private Integer maxPoolPreparedStatementPerConnectionSize = 100;

    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。
     * 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
     */
    private String validationQuery;

    /**
     * 单位：秒，检测连接是否有效的超时时间。底层调用jdbc Statement对象的void setQueryTimeout(int seconds)方法
     */
    private Integer validationQueryTimeout = 10;

    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    private Boolean testOnBorrow = true;

    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    private Boolean testOnReturn = true;

    /**
     * 建议配置为true，不影响性能，并且保证安全性。
     * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
     */
    private Boolean testWhileIdle = true;

    /**
     * 连接池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作。
     */
    private Boolean keepAlive = false;

    /**
     * 有两个含义：
     * 1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于 minEvictableIdleTimeMillis 则关闭物理连接。
     * 2) testWhileIdle 的判断依据，详细看 testWhileIdle 属性的说明
     */
    private Integer timeBetweenEvictionRunsMillis = 60000;

    /**
     * 连接保持空闲而不被驱逐的最小时间
     */
    private Integer minEvictableIdleTimeMillis = 300000;

    /**
     * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
     * 监控统计用的filter:stat
     * 日志用的filter:log4j
     * 防御sql注入的filter:wall
     */
    private String filters = "stat";

}
