package cn.ibenbeni.bens.message.center.modular.handler.config;

import cn.ibenbeni.bens.message.center.common.config.MessageCenterProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 消息中心-消息处理层-自动注入类
 */
@EnableConfigurationProperties(value = {MessageCenterProperties.class})
@Configuration
public class MessageHandlerAutoConfiguration {
}
