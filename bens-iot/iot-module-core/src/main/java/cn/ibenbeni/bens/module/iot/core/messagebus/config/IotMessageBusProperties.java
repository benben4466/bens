package cn.ibenbeni.bens.module.iot.core.messagebus.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * IOT-消息总线配置属性
 */
@Data
@Validated
@ConfigurationProperties("bens.iot.message-bus")
public class IotMessageBusProperties {

    /**
     * 消息总线类型
     * <p>可选值：local、redis、rocketmq、rabbitmq</p>
     */
    @NotNull(message = "IoT消息总线类型不能为空")
    private String type = "local";

}
