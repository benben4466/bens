package cn.ibenbeni.bens.websocket.starter;

import cn.ibenbeni.bens.websocket.api.listener.WebSocketMessageListener;
import cn.ibenbeni.bens.websocket.api.prop.WebSocketProperties;
import cn.ibenbeni.bens.websocket.api.session.WebSocketSessionManager;
import cn.ibenbeni.bens.websocket.sdk.handler.JsonWebSocketMessageHandler;
import cn.ibenbeni.bens.websocket.sdk.security.LoginUserHandshakeInterceptor;
import cn.ibenbeni.bens.websocket.sdk.sender.local.LocalWebSocketMessageSender;
import cn.ibenbeni.bens.websocket.sdk.session.WebSocketSessionHandlerDecorator;
import cn.ibenbeni.bens.websocket.sdk.session.WebSocketSessionManagerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;

/**
 * WebSocket 自动配置类
 */
@EnableWebSocket // 开启 websocket, 即激活 Spring 的 WebSocket 编程模型
@ConditionalOnProperty(prefix = "bens.websocket", value = "enable", matchIfMissing = true) // 允许使用 WebSocket 模块
@EnableConfigurationProperties(WebSocketProperties.class)
public class BensWebSocketAutoConfiguration {

    /**
     * 原生 WebSocket 的地址路由器
     * <p>把“某个 URL 路径”映射到“某个 WebSocketHandler”</p>
     *
     * @param handshakeInterceptors 握手拦截器
     * @param webSocketHandler      WebSocketHandler
     * @param webSocketProperties   项目 WebSocket 配置
     */
    @Bean
    public WebSocketConfigurer webSocketConfigurer(HandshakeInterceptor[] handshakeInterceptors,
                                                   WebSocketHandler webSocketHandler,
                                                   WebSocketProperties webSocketProperties) {
        return registry -> registry
                // 添加 WebSocketHandler
                .addHandler(webSocketHandler, webSocketProperties.getPath())
                // 设置握手拦截器
                .addInterceptors(handshakeInterceptors)
                // 允许跨域，否则前端连接会直接断开
                .setAllowedOriginPatterns("*");
    }

    /**
     * 负责处理 WebSocket 生命周期的全部事件
     *
     * @param sessionManager   WebSocket Session 管理器
     * @param messageListeners WebSocket 消息监听器
     */
    @Bean
    public WebSocketHandler webSocketHandler(WebSocketSessionManager sessionManager,
                                             List<? extends WebSocketMessageListener<?>> messageListeners) {
        // 1. 创建 JsonWebSocketMessageHandler 对象，处理消息
        JsonWebSocketMessageHandler messageHandler = new JsonWebSocketMessageHandler(messageListeners);
        // 2. 创建 WebSocketSessionHandlerDecorator 对象，处理连接
        return new WebSocketSessionHandlerDecorator(messageHandler, sessionManager);
    }

    /**
     * 处理 WebSocket 握手过程中的 HTTP 请求，可以拦截和修改握手请求及响应。
     */
    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new LoginUserHandshakeInterceptor();
    }

    /**
     * WebSocket Session 管理器
     */
    @Bean
    public WebSocketSessionManager webSocketSessionManager() {
        return new WebSocketSessionManagerImpl();
    }

    // ==================== Sender 相关 ====================

    /**
     * 本地消息发送器
     */
    @Configuration
    @ConditionalOnProperty(prefix = "bens.websocket", name = "sender-type", havingValue = "local")
    public class LocalWebSocketMessageSenderConfiguration {

        @Bean
        public LocalWebSocketMessageSender localWebSocketMessageSender(WebSocketSessionManager sessionManager) {
            return new LocalWebSocketMessageSender(sessionManager);
        }

    }

}
