package cn.ibenbeni.bens.module.iot.gateway.protocol.emqx;

import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import cn.ibenbeni.bens.module.iot.gateway.config.IotGatewayProperties;
import cn.ibenbeni.bens.module.iot.gateway.protocol.emqx.router.IotEmqxAuthEventHandler;
import cn.ibenbeni.bens.module.iot.gateway.util.IotMqttTopicUtils;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * EMQX认证事件协议
 */
@Slf4j
public class IotEmqxAuthEventProtocol {

    /**
     * EMQX配置参数
     */
    private final IotGatewayProperties.EmqxProperties emqxProperties;

    /**
     * 服务ID
     */
    private final String serverId;

    private final Vertx vertx;

    /**
     * HTTP服务
     */
    private HttpServer httpServer;

    public IotEmqxAuthEventProtocol(IotGatewayProperties.EmqxProperties emqxProperties, Vertx vertx) {
        this.vertx = vertx;
        this.emqxProperties = emqxProperties;
        this.serverId = IotDeviceMessageUtils.generateServerId(emqxProperties.getMqttPort());
    }

    @PostConstruct
    public void start() {
        try {
            startHttpServer();
            log.info("[start][IoT 网关 EMQX 认证事件协议服务启动成功, 端口: {}]", emqxProperties.getHttpPort());
        } catch (Exception ex) {
            log.error("[start][IoT 网关 EMQX 认证事件协议服务启动失败]", ex);
        }
    }

    @PreDestroy
    public void stop() {
        stopHttpServer();
        log.info("[stop][IoT 网关 EMQX 认证事件协议服务已停止]");
    }

    /**
     * 启动 HTTP 服务
     */
    private void startHttpServer() {
        Integer httpPort = emqxProperties.getHttpPort();

        // 1.创建路由
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        // 2.创建处理器
        IotEmqxAuthEventHandler handler = new IotEmqxAuthEventHandler(serverId);
        // 注册 POST 请求，并且注册请求对应的处理器
        router.post(IotMqttTopicUtils.MQTT_AUTH_PATH).handler(handler::handleAuth);
        router.post(IotMqttTopicUtils.MQTT_EVENT_PATH).handler(handler::handleEvent);

        try {
            // vertx.createHttpServer() 创建 HTTP 服务器示例
            // requestHandler(Handler<HttpServerRequest> handler)：指定请求处理器
            // listen()：监听指定端口
            // result()：从异步结果 (Future<HttpServer>) 中取出真正的 HttpServer 对象
            httpServer = vertx.createHttpServer()
                    .requestHandler(router)
                    .listen(httpPort)
                    .result();
        } catch (Exception ex) {
            log.error("[startHttpServer][HTTP 服务器启动失败, 端口: {}]", httpPort, ex);
            throw ex;
        }
    }

    /**
     * 停止 HTTP 服务
     */
    private void stopHttpServer() {
        if (httpServer == null) {
            return;
        }

        try {
            // httpServer.close() 异步关闭服务
            httpServer.close(ar -> {
                if (ar.succeeded()) {
                    log.info("[stopHttpServer][HTTP 服务器已停止]");
                } else {
                    log.error("[stopHttpServer][HTTP 服务器停止失败]", ar.cause());
                }
            });
        } catch (Exception ex) {
            log.error("[stopHttpServer][HTTP 服务器停止失败]", ex);
        }
    }

}
