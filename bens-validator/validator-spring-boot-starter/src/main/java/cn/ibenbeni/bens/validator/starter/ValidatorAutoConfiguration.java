package cn.ibenbeni.bens.validator.starter;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 校验器自动配置类
 * <p>本自动配置类为什么要在ValidationAutoConfiguration之前加载？因为ValidationAutoConfiguration类中会加载默认参数校验器</p>
 *
 * @author: benben
 * @time: 2025/6/15 下午4:48
 */
@Configuration
@AutoConfigureBefore(ValidationAutoConfiguration.class)
public class ValidatorAutoConfiguration {

    /**
     * 注入自定义Spring参数校验器
     * <p>作用：保存validator中读取不到的信息</p>
     */
    @Bean
    public CacheVerificationGroup bensValidator() {
        return new CacheVerificationGroup();
    }

}
