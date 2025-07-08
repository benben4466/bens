package cn.ibenbeni.bens.db.starter.mp;

import cn.ibenbeni.bens.db.mp.fieldfill.CustomMetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-Plus默认配置
 *
 * @author benben
 * @date 2025/4/19  下午1:06
 */
@MapperScan("cn.ibenbeni.**.mapper")
@Configuration
public class MybatisPlusAutoConfiguration {

    /**
     * Mybatis-Plus 自动填充配置
     */
    @Bean
    public CustomMetaObjectHandler customMetaObjectHandler() {
        return new CustomMetaObjectHandler();
    }

    /**
     * Mybatis-Plus 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 注意：多租户插件需在分页插件之前

        // 添加多租户插件（若开启）BensTenantAutoConfiguration内添加

        // 添加分页插件
        interceptor.addInnerInterceptor(this.paginationInnerInterceptor());

        // 添加乐观锁插件
        interceptor.addInnerInterceptor(this.optimisticLockerInnerInterceptor());

        return interceptor;
    }

    /**
     * Mybatis-Plus 分页插件
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }

    /**
     * Mybatis-Plus 乐观锁插件
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

}
