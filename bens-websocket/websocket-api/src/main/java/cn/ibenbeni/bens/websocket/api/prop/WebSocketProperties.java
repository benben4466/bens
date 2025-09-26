package cn.ibenbeni.bens.websocket.api.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Validated
@ConfigurationProperties("bens.websocket")
public class WebSocketProperties {

    /**
     * WebSocket 的连接路径
     */
    @NotEmpty(message = "WebSocket的连接路径不能为空")
    private String path = "/ws";

    /**
     * 消息发送器的类型
     * <p>可选值：local、redis、rocketmq、kafka、rabbitmq</p>
     * <p>暂时可选：local</p>
     */
    @NotNull(message = "WebSocket的消息发送者不能为空")
    private String senderType = "local";

}
