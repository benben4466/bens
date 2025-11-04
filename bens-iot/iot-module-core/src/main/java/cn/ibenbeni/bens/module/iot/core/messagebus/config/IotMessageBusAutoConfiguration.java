package cn.ibenbeni.bens.module.iot.core.messagebus.config;

import cn.ibenbeni.bens.module.iot.core.messagebus.core.IotMessageBus;
import cn.ibenbeni.bens.module.iot.core.messagebus.core.local.IotLocalMessageBus;
import cn.ibenbeni.bens.module.iot.core.messagebus.core.rocketmq.IotRocketMQMessageBus;
import cn.ibenbeni.bens.module.iot.core.mq.producer.IotDeviceMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * IoT-消息总线-自动配置
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(IotMessageBusProperties.class)
public class IotMessageBusAutoConfiguration {

    /**
     * 注入设备消息生产者
     *
     * @param messageBus 消息总线
     * @return 设备消息生产者
     */
    @Bean
    public IotDeviceMessageProducer deviceMessageProducer(IotMessageBus messageBus) {
        return new IotDeviceMessageProducer(messageBus);
    }

    // region 消息总线-Local实现

    @Configuration
    @ConditionalOnProperty(prefix = "bens.iot.message-bus", name = "type", havingValue = "local", matchIfMissing = true)
    public static class IotLocalMessageBusConfiguration {
        @Bean
        public IotLocalMessageBus iotLocalMessageBus(ApplicationContext applicationContext) {
            log.info("[iotLocalMessageBus][创建 IoT Local 消息总线]");
            return new IotLocalMessageBus(applicationContext);
        }
    }

    // endregion

    // region 消息总线-RocketMQ实现

    @Configuration
    @ConditionalOnClass(RocketMQTemplate.class)
    @ConditionalOnProperty(prefix = "bens.iot.message-bus", name = "type", havingValue = "rocketmq")
    public static class IotRocketMQMessageBusConfiguration {

        @Bean
        public IotRocketMQMessageBus iotRocketMQMessageBus(RocketMQProperties rocketMQProperties, RocketMQTemplate rocketMQTemplate) {
            log.info("[iotRocketMQMessageBus][创建 IoT RocketMQ 消息总线]");
            return new IotRocketMQMessageBus(rocketMQProperties, rocketMQTemplate);
        }

    }

    // endregion

}
