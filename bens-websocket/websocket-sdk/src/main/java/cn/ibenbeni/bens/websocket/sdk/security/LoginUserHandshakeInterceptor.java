package cn.ibenbeni.bens.websocket.sdk.security;

import cn.ibenbeni.bens.auth.api.context.LoginUserHolder;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.websocket.sdk.util.WebSocketFrameworkUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * 登陆用户 {@link HandshakeInterceptor} 实现类
 * <p>
 * 流程如下：
 * 1.前端连接 WebSocket 时，会通过拼接 ?token={token} 到 ws:// 连接后，这样它可以被 给予 HTTP Token认证
 * 2.LoginUserHandshakeInterceptor 将登陆用户添加到 {@link WebSocketSession} 中
 * <p>
 * 作用：
 * 1.WebSocket模块里的一个 “拦截器” 接口，专门用于在 WebSocket 握手阶段（即 HTTP 升级成 WebSocket 之前）插入自定义逻辑。
 * 2.负责 打开连接前 和 连接建立后 的“夹带私货”——鉴权、属性传递、日志、拒绝非法请求等。
 */
public class LoginUserHandshakeInterceptor implements HandshakeInterceptor {

    /**
     * <p>握手前调用</p>
     * <p>返回值：true=继续握手；false=立即终止，不会建立 WebSocket 连接。</p>
     * <p>作用：鉴权（JWT、Cookie、Header）、拒绝非法 Origin、把用户信息塞进 attributes 供后续 WebSocketHandler 使用。</p>
     *
     * @param request    请求
     * @param response   响应
     * @param wsHandler  负责处理 WebSocket 消息的那个 Bean，你自己写的 TextWebSocketHandler 或 BinaryWebSocketHandler）
     * @param attributes 属性
     * @return boolean
     * @throws Exception 抛异常会被 Spring 捕获并转成握手失败，同样不会建立连接。
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        LoginUser loginUser = LoginUserHolder.get();
        if (loginUser != null) {
            WebSocketFrameworkUtils.setLoginUser(loginUser, attributes);
        }
        return true;
    }

    /**
     * <p>握手成功后调用(无论 beforeHandshake 返回 true 还是 false 都会进入)</p>
     * <p>调用时机：握手响应已提交（101 已发出），连接建立成功 或 因 beforeHandshake 返回 false / 抛异常而失败；总会执行。</p>
     * <p>作用：记录审计日志、释放 ThreadLocal 资源、统计握手成功率、对失败场景做告警。</p>
     *
     * @param request   请求
     * @param response  响应
     * @param wsHandler
     * @param exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // do nothing
    }

}
