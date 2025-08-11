package cn.ibenbeni.bens.config.modular.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.ibenbeni.bens.config.api.context.ConfigContext;
import cn.ibenbeni.bens.config.api.exception.ConfigException;
import cn.ibenbeni.bens.config.api.exception.enums.ConfigExceptionEnum;
import cn.ibenbeni.bens.config.sdk.storage.memory.MemoryConfigContainer;
import cn.ibenbeni.bens.config.sdk.storage.redis.RedisConfigContainer;
import cn.ibenbeni.bens.rule.enums.YesOrNotEnum;
import cn.ibenbeni.bens.rule.listener.ContextRefreshedListener;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 参数配置初始化监听器
 * <p>
 * 作用：
 * 1.创建参数存储容器
 * 2.填充参数存储容器
 * </p>
 */
@Slf4j
public class ConfigInitListener extends ContextRefreshedListener implements Ordered {

    private DynamicRoutingDataSource dataSource;

    @Override
    public void eventCallback(ContextRefreshedEvent event) {
        // 获取环境变量对象
        Environment environment = event.getApplicationContext().getEnvironment();
        dataSource = event.getApplicationContext().getBean(DynamicRoutingDataSource.class);

        if (getRedisEnableFlag()) {
            // 启用Redis
            String redisHost = environment.getProperty("spring.data.redis.host");
            String redisPort = environment.getProperty("spring.data.redis.port");
            String redisPassword = environment.getProperty("spring.data.redis.password");
            String dbNumber = environment.getProperty("spring.data.redis.database");

            // 初始化Redis容器
            ConfigContext.setConfigApi(new RedisConfigContainer(redisHost, Convert.toInt(redisPort, 6379), redisPassword, Convert.toInt(dbNumber, 0)));
        } else {
            // 默认初始化内存容器
            ConfigContext.setConfigApi(new MemoryConfigContainer());
        }

        // 获取主数据源
        DataSource primaryDataSource = dataSource.getDataSource(null);
        if (primaryDataSource == null) {
            throw new ConfigException(ConfigExceptionEnum.DB_CONFIG_ERROR);
        }

        Connection conn = null;
        try {
            conn = primaryDataSource.getConnection();

            // 手写SQL
            // 获取参数
            String querySqlTemplate = "select config_code, config_value from sys_config where del_flag = ?";
            List<Entity> entityList = SqlExecutor.query(conn, querySqlTemplate, new EntityListHandler(), YesOrNotEnum.N.getCode());
            if (CollUtil.isNotEmpty(entityList)) {
                entityList.forEach(config -> {
                    ConfigContext.me().putConfig(config.getStr("config_code"), config.getStr("config_value"));
                });
            }
        } catch (SQLException sqlException) {
            log.error("初始化系统配置表失败，执行查询语句失败", sqlException);
        } finally {
            DbUtil.close(conn);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 100;
    }

    private boolean getRedisEnableFlag() {
        try {
            // 尝试加载Redis工厂类
            Class.forName("org.springframework.data.redis.connection.RedisConnectionFactory");
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
