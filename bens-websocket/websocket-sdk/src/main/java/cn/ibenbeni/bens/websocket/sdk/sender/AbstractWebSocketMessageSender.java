package cn.ibenbeni.bens.websocket.sdk.sender;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.websocket.api.message.JsonWebSocketMessage;
import cn.ibenbeni.bens.websocket.api.sender.WebSocketMessageSender;
import cn.ibenbeni.bens.websocket.api.session.WebSocketSessionManager;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * WebSocketMessageSender 实现类
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractWebSocketMessageSender implements WebSocketMessageSender {

    /**
     * WebSocket Session 管理器
     */
    private final WebSocketSessionManager sessionManager;

    @Override
    public void send(Integer userType, String messageType, String messageContent) {
        send(null, userType, null, messageType, messageContent);
    }

    @Override
    public void send(Integer userType, Long userId, String messageType, String messageContent) {
        send(null, userType, userId, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        send(sessionId, null, null, messageType, messageContent);
    }

    /**
     * 发送消息
     *
     * @param sessionId      SessionID
     * @param userType       用户类型
     * @param userId         用户ID
     * @param messageType    消息类型
     * @param messageContent 消息内容
     */
    public void send(String sessionId, Integer userType, Long userId, String messageType, String messageContent) {
        List<WebSocketSession> sessions = Collections.emptyList();
        if (StrUtil.isNotBlank(sessionId)) {
            WebSocketSession session = sessionManager.getSession(sessionId);
            if (session != null) {
                sessions = Collections.singletonList(session);
            }
        } else if (userType != null && userId != null) {
            sessions = (List<WebSocketSession>) sessionManager.getSessionList(userType, userId);
        } else if (userType != null) {
            sessions = (List<WebSocketSession>) sessionManager.getSessionList(userType);
        }

        if (CollUtil.isEmpty(sessions)) {
            log.debug("[send][sessionId({}) userType({}) userId({}) messageType({}) messageContent({}) 未匹配到会话]", sessionId, userType, userId, messageType, messageContent);
        }

        // 发送消息
        doSend(sessions, messageType, messageContent);
    }

    /**
     * 发送消息的具体实现
     *
     * @param sessions       Session 列表
     * @param messageType    消息类型
     * @param messageContent 消息内容
     */
    public void doSend(Collection<WebSocketSession> sessions, String messageType, String messageContent) {
        JsonWebSocketMessage message = JsonWebSocketMessage.builder()
                .type(messageType)
                .content(messageContent)
                .build();
        String payload = JSON.toJSONString(message);
        sessions.forEach(session -> {
            if (session == null) {
                log.error("[doSend][session 为空, message({})]", message);
                return;
            }
            if (!session.isOpen()) {
                log.error("[doSend][session({}) 已关闭, message({})]", session.getId(), message);
                return;
            }

            // 发送消息
            try {
                session.sendMessage(new TextMessage(payload));
                log.info("[doSend][session({}) 发送消息成功，message({})]", session.getId(), message);
            } catch (IOException ex) {
                log.error("[doSend][session({}) 发送消息失败，message({})]", session.getId(), message, ex);
            }
        });
    }

}
