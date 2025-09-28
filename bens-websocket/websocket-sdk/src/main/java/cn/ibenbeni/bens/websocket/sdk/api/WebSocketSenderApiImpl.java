package cn.ibenbeni.bens.websocket.sdk.api;

import cn.ibenbeni.bens.websocket.api.WebSocketSenderApi;
import cn.ibenbeni.bens.websocket.api.sender.WebSocketMessageSender;

/**
 * WebSocket 发送器的 API 实现类
 */
public class WebSocketSenderApiImpl implements WebSocketSenderApi {

    private final WebSocketMessageSender webSocketMessageSender;

    public WebSocketSenderApiImpl(WebSocketMessageSender webSocketMessageSender) {
        this.webSocketMessageSender = webSocketMessageSender;
    }

    @Override
    public void send(Integer userType, Long userId, String messageType, String messageContent) {
        webSocketMessageSender.send(userType, userId, messageType, messageContent);
    }

    @Override
    public void send(Integer userType, String messageType, String messageContent) {
        webSocketMessageSender.send(userType, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        webSocketMessageSender.send(sessionId, messageType, messageContent);
    }

}
