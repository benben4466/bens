package cn.ibenbeni.bens.websocket.api.message;

import cn.ibenbeni.bens.websocket.api.listener.WebSocketMessageListener;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * JSON 格式的 WebSocket 消息
 */
@Data
@Builder
public class JsonWebSocketMessage implements Serializable {

    /**
     * 消息类型
     * <p>
     * 目的：分发到对应的{@link WebSocketMessageListener}消息处理器
     */
    private String type;

    /**
     * 消息内容
     * <p>要求：JSON格式</p>
     */
    private String content;

}
