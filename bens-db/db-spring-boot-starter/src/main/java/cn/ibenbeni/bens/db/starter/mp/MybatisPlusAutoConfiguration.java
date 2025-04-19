package cn.ibenbeni.bens.db.starter.mp;

import cn.ibenbeni.bens.db.mp.fieldfill.CustomMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-Plus默认配置
 *
 * @author benben
 * @date 2025/4/19  下午1:06
 */
@Configuration
public class MybatisPlusAutoConfiguration {

    /**
     * Mybatis-Plus 自动填充配置
     */
    @Bean
    public CustomMetaObjectHandler customMetaObjectHandler() {
        return new CustomMetaObjectHandler();
    }

}
