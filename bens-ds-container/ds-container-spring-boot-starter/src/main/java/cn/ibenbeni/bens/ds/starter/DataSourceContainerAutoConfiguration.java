package cn.ibenbeni.bens.ds.starter;

import cn.ibenbeni.bens.ds.sdk.DynamicDataSource;
import cn.ibenbeni.bens.ds.sdk.aop.MultiSourceExchangeAop;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 多数据源自动配置
 *
 * @author: benben
 * @time: 2025/6/25 下午2:50
 */
@Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@ConditionalOnMissingBean(DataSource.class)
public class DataSourceContainerAutoConfiguration {

    /**
     * 多数据源连接池
     */
    @Bean
    public DynamicDataSource dataSource() {
        return new DynamicDataSource();
    }

    /**
     * 注入动态数据源切换AOP
     */
    @Bean
    public MultiSourceExchangeAop multiSourceExchangeAop() {
        return new MultiSourceExchangeAop();
    }

}
