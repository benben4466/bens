package cn.ibenbeni.bens.db.starter.mp;

import cn.ibenbeni.bens.db.api.pojo.db.DbProp;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库连接和DAO框架的自动配置
 *
 * @author: benben
 * @time: 2025/6/16 下午10:40
 */
@Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class BensDbPropAutoConfiguration {

    /**
     * 数据库连接属性
     * <p>@ConfigurationProperties注解将数据库连接属性注入到Bean中</p>
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    @ConditionalOnMissingBean(DbProp.class)
    public DbProp dbProp() {
        return new DbProp();
    }

}
