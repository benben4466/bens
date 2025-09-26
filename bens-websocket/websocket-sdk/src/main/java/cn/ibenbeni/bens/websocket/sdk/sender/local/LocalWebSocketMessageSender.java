package cn.ibenbeni.bens.websocket.sdk.sender.local;

import cn.ibenbeni.bens.websocket.api.sender.WebSocketMessageSender;
import cn.ibenbeni.bens.websocket.api.session.WebSocketSessionManager;
import cn.ibenbeni.bens.websocket.sdk.sender.AbstractWebSocketMessageSender;

/**
 * 本地{@link WebSocketMessageSender} 实现类
 * 应用场景：单机
 */
public class LocalWebSocketMessageSender extends AbstractWebSocketMessageSender {

    public LocalWebSocketMessageSender(WebSocketSessionManager sessionManager) {
        super(sessionManager);
    }

}
