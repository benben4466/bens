package cn.ibenbeni.bens.iot.modular.base.framework.core.config;

import cn.ibenbeni.bens.iot.modular.base.framework.core.schedule.IotSchedulerManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * IOT模块-Job配置类
 */
@Configuration
public class IotJobConfiguration {

    /**
     * 注入 IotSchedulerManager Bean
     *
     * @param dataSource         数据源
     * @param applicationContext 上下文
     * @return IotSchedulerManager Bean
     */
    @Bean
    public IotSchedulerManager iotSchedulerManager(DataSource dataSource, ApplicationContext applicationContext) {
        return new IotSchedulerManager(dataSource, applicationContext);
    }

}
