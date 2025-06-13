package cn.ibenbeni.bens.event.starter;

import cn.ibenbeni.bens.event.sdk.EventAnnotationScanner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 业务事件自动装配
 *
 * @author: benben
 * @time: 2025/6/13 下午5:25
 */
@Configuration
public class EventAutoConfiguration {

    /**
     * 事件扫描扫描器
     */
    @Bean
    @ConditionalOnMissingBean(EventAnnotationScanner.class)
    public EventAnnotationScanner eventAnnotationScanner() {
        return new EventAnnotationScanner();
    }

}
