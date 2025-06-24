package cn.ibenbeni.bens.file.starter;

import cn.ibenbeni.bens.file.api.FileOperatorApi;
import cn.ibenbeni.bens.file.local.LocalFileOperator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件模块自动配置
 *
 * @author: benben
 * @time: 2025/6/22 下午5:06
 */
@Configuration
public class BensFileAutoConfiguration {

    /**
     * 注入文件操作实现
     * <p>默认本地存储</p>
     */
    @Bean
    @ConditionalOnMissingBean(FileOperatorApi.class)
    public FileOperatorApi fileOperatorApi() {
        return new LocalFileOperator();
    }

}
