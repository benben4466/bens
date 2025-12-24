package cn.ibenbeni.bens.module.iot.gateway.protocol.emqx;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import cn.ibenbeni.bens.module.iot.gateway.config.IotGatewayProperties;
import cn.ibenbeni.bens.module.iot.gateway.protocol.emqx.router.IotEmqxUpstreamHandler;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.JksOptions;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * IOT-网关 EMQX 协议：接收设备上行消息
 */
@Slf4j
public class IotEmqxUpstreamProtocol {

    /**
     * EMQX 配置属性
     */
    private final IotGatewayProperties.EmqxProperties emqxProperties;

    private final Vertx vertx;

    /**
     * MQTT 客户端
     */
    private MqttClient mqttClient;

    /**
     * 是否正在运行
     */
    private volatile boolean isRunning = false;

    /**
     * 服务器编号
     */
    @Getter
    private String serverId;

    /**
     * EMQX 上行消息处理器
     */
    private IotEmqxUpstreamHandler upstreamHandler;

    public IotEmqxUpstreamProtocol(IotGatewayProperties.EmqxProperties emqxProperties, Vertx vertx) {
        this.vertx = vertx;
        this.emqxProperties = emqxProperties;
        this.serverId = IotDeviceMessageUtils.generateServerId(emqxProperties.getMqttPort());
    }

    @PostConstruct
    public void start() {
        if (isRunning) {
            return;
        }

        try {
            // 1.启动 MQTT 客户端
            startMqttClient();

            // 2.标记服务正在运行
            isRunning = true;
            log.info("[start][IoT 网关 EMQX 协议启动成功]");
        } catch (Exception ex) {
            log.error("[start][IoT 网关 EMQX 协议服务启动失败，应用将关闭]", ex);
            stop();

            // 异步关闭应用
            Thread shutdownThread = new Thread(() -> {
                try {
                    // 确保日志输出完成，使用更优雅的方式
                    log.error("[start][由于 MQTT 连接失败，正在关闭应用]");
                    // 等待日志输出完成
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    log.warn("[start][应用关闭被中断]");
                }
                System.exit(1);
            });
            shutdownThread.setDaemon(true);
            shutdownThread.setName("emergency-shutdown");
            shutdownThread.start();

            throw ex;
        }
    }

    /**
     * 启动客户端
     */
    private void startMqttClient() {
        try {
            // 1.初始化消息处理器
            this.upstreamHandler = new IotEmqxUpstreamHandler(this);
            // 2.创建 MQTT 客户端
            createMqttClient();
            // 3.同步连接 MQTT Broker
            connectMqttSync();
        } catch (Exception ex) {
            log.error("[startMqttClient][MQTT 客户端启动失败]", ex);
            throw new RuntimeException("MQTT 客户端启动失败: " + ex.getMessage(), ex);
        }
    }

    /**
     * 同步连接 MQTT Broker
     */
    private void connectMqttSync() {
        String host = emqxProperties.getMqttHost();
        int port = emqxProperties.getMqttPort();

        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean success = new AtomicBoolean(false);

        // 1.连接 MQTT Broker
        mqttClient.connect(port, host, connectResult -> {
            if (connectResult.succeeded()) {
                log.info("[connectMqttSync][MQTT 客户端连接成功, host: {}, port: {}]", host, port);
                setupMqttHandlers();
                subscribeToTopics();
                success.set(true);
            } else {
                log.error("[connectMqttSync][连接 MQTT Broker 失败, host: {}, port: {}]", host, port, connectResult.cause());
            }
            latch.countDown();
        });

        // 2.等待连接结果
        try {
            boolean awaitResult = latch.await(10, TimeUnit.SECONDS);
            if (!awaitResult) {
                log.error("[connectMqttSync][等待连接结果超时]");
                throw new RuntimeException("连接 MQTT Broker 超时");
            }

            if (!success.get()) {
                throw new RuntimeException(String.format("首次连接 MQTT Broker 失败, 地址: %s, 端口: %d", host, port));
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.error("[connectMqttSync][等待连接结果被中断]", ex);
            throw new RuntimeException("连接 MQTT Broker 被中断", ex);
        }
    }

    /**
     * 异步连接 MQTT Broker
     */
    private void connectMqttAsync() {
        String host = emqxProperties.getMqttHost();
        int port = emqxProperties.getMqttPort();
        mqttClient.connect(port, host, connectResult -> {
            if (connectResult.succeeded()) {
                log.info("[connectMqttAsync][MQTT 客户端重连成功]");
                setupMqttHandlers();
                subscribeToTopics();
            } else {
                log.error("[connectMqttAsync][连接 MQTT Broker 失败, host: {}, port: {}]", host, port, connectResult.cause());
                log.warn("[connectMqttAsync][重连失败, 将再次尝试]");
                reconnectWithDelay();
            }
        });
    }

    /**
     * 延迟重连
     */
    private void reconnectWithDelay() {
        if (!isRunning) {
            return;
        }
        if (mqttClient != null && mqttClient.isConnected()) {
            return;
        }

        long delay = emqxProperties.getReconnectDelayMs(); // 延迟时间(毫秒)
        log.info("[reconnectWithDelay][将在 {} 毫秒后尝试重连 MQTT Broker]", delay);
        vertx.setTimer(delay, timerId -> {
            if (!isRunning) {
                return;
            }
            if (mqttClient != null && mqttClient.isConnected()) {
                return;
            }

            log.info("[reconnectWithDelay][开始重连 MQTT Broker]");
            try {
                createMqttClient();
                connectMqttAsync();
            } catch (Exception ex) {
                log.error("[reconnectWithDelay][重连过程中发生异常]", ex);
                vertx.setTimer(delay, t -> reconnectWithDelay());
            }
        });
    }

    /**
     * 关闭客户端
     * <p>@PreDestroy注解：在Bean销毁时调用该方法</p>
     */
    @PreDestroy
    public void stop() {
        if (!isRunning) {
            return;
        }

        // 1.停止 MQTT 客户端
        stopMqttClient();

        // 2.标记服务停止
        isRunning = false;
        log.info("[stop][IoT 网关 MQTT 协议服务已停止]");
    }

    /**
     * 停止 MQTT 客户端
     */
    private void stopMqttClient() {
        if (mqttClient == null) {
            return;
        }

        try {
            if (mqttClient.isConnected()) {
                // 1.取消订阅所有主题
                List<String> topicList = emqxProperties.getMqttTopics();
                for (String topic : topicList) {
                    try {
                        mqttClient.unsubscribe(topic);
                    } catch (Exception ex) {
                        log.warn("[stopMqttClient][取消订阅主题({})异常]", topic, ex);
                    }
                }

                // 2.断开 MQTT 客户端连接
                try {
                    CountDownLatch disconnectLatch = new CountDownLatch(1);
                    mqttClient.disconnect(dh -> disconnectLatch.countDown());
                    if (!disconnectLatch.await(5, TimeUnit.SECONDS)) {
                        log.warn("[stopMqttClient][断开 MQTT 连接超时]");
                    }
                } catch (Exception ex) {
                    log.warn("[stopMqttClient][关闭 MQTT 客户端异常]", ex);
                }
            }
        } catch (Exception ex) {
            log.warn("[stopMqttClient][停止 MQTT 客户端过程中发生异常]", ex);
        } finally {
            mqttClient = null;
        }
    }

    /**
     * 设置 MQTT 处理器
     * <p>
     * 1.设置断开连接监听器：断开连接时，重新连接
     * 2.设置异常处理器
     * 3.设置消息处理器
     * </p>
     */
    private void setupMqttHandlers() {
        // 1.设置断开连接监听器：断开连接时，重新连接
        mqttClient.closeHandler(closeEvent -> {
            if (!isRunning) {
                return;
            }
            log.warn("[closeHandler][MQTT 连接已断开, 准备重连]");
            reconnectWithDelay();
        });

        // 2.设置异常处理器
        mqttClient.exceptionHandler(exception -> {
            log.error("[exceptionHandler][MQTT 客户端异常]", exception);
        });

        // 3.设置消息处理器
        mqttClient.publishHandler(upstreamHandler::handle);
    }

    /**
     * 订阅设备上行消息主题
     */
    private void subscribeToTopics() {
        // 1.校验 MQTT 客户端是否连接
        List<String> topicList = emqxProperties.getMqttTopics();
        if (mqttClient == null || !mqttClient.isConnected()) {
            log.warn("[subscribeToTopics][MQTT 客户端未连接, 跳过订阅]");
            return;
        }

        // 2.批量订阅所有主题
        Map<String, Integer> topics = new HashMap<>();
        int qos = emqxProperties.getMqttQos();
        for (String topic : topicList) {
            topics.put(topic, qos);
        }
        mqttClient.subscribe(topics, subscribeResult -> {
            if (subscribeResult.succeeded()) {
                log.info("[subscribeToTopics][订阅主题成功, 共 {} 个主题]", topicList.size());
            } else {
                log.error("[subscribeToTopics][订阅主题失败, 共 {} 个主题, 原因: {}]", topicList.size(), subscribeResult.cause().getMessage(), subscribeResult.cause());
            }
        });
    }

    /**
     * 创建 MQTT 客户端
     */
    private void createMqttClient() {
        // 1构造 MQTT 配置项
        MqttClientOptions options = (MqttClientOptions) new MqttClientOptions()
                .setClientId(emqxProperties.getMqttClientId())
                .setUsername(emqxProperties.getMqttUsername())
                .setPassword(emqxProperties.getMqttPassword())
                .setSsl(emqxProperties.getMqttSsl())
                .setCleanSession(emqxProperties.getCleanSession())
                .setKeepAliveInterval(emqxProperties.getKeepAliveIntervalSeconds())
                .setMaxInflightQueue(emqxProperties.getMaxInflightQueue())
                .setConnectTimeout(emqxProperties.getConnectTimeoutSeconds() * 1000)
                .setTrustAll(emqxProperties.getTrustAll());
        // 1.2 设置遗嘱消息
        IotGatewayProperties.EmqxProperties.Will will = emqxProperties.getWill();
        if (will.isEnabled()) {
            Assert.notBlank(will.getTopic(), "遗嘱消息主题(will.topic)不能为空");
            Assert.notNull(will.getPayload(), "遗嘱消息内容(will.payload)不能为空");
            options.setWillFlag(true)
                    .setWillTopic(will.getTopic())
                    .setWillMessage(will.getPayload())
                    .setWillQoS(will.getQos())
                    .setWillRetain(will.isRetain());
        }
        // 1.3 设置高级 SSL/TLS（仅在启用 SSL 且不信任所有证书时生效）
        if (Boolean.TRUE.equals(emqxProperties.getMqttSsl()) && !Boolean.TRUE.equals(emqxProperties.getTrustAll())) {
            IotGatewayProperties.EmqxProperties.Ssl sslOptions = emqxProperties.getSslOptions();
            if (StrUtil.isNotBlank(sslOptions.getTrustStorePath())) {
                options.setTrustStoreOptions(new JksOptions()
                        .setPath(sslOptions.getTrustStorePath())
                        .setPassword(sslOptions.getTrustStorePassword())
                );
            }
            if (StrUtil.isNotBlank(sslOptions.getKeyStorePath())) {
                options.setKeyStoreOptions(new JksOptions()
                        .setPath(sslOptions.getKeyStorePath())
                        .setPassword(sslOptions.getKeyStorePassword())
                );
            }
        }
        // 1.4 安全警告日志
        if (Boolean.TRUE.equals(emqxProperties.getTrustAll())) {
            log.warn("[createMqttClient][安全警告: 当前配置信任所有 SSL 证书(trustAll=true), 这在生产环境中存在严重安全风险!]");
        }

        // 2.创建 MQTT 客户端
        this.mqttClient = MqttClient.create(vertx, options);
    }

    /**
     * 发布消息到 MQTT Broker
     *
     * @param topic   主题
     * @param payload 消息内容
     */
    public void publishMessage(String topic, byte[] payload) {
        if (mqttClient == null || !mqttClient.isConnected()) {
            log.warn("[publishMessage][MQTT 客户端未连接, 无法发布消息]");
            return;
        }

        MqttQoS qos = MqttQoS.valueOf(emqxProperties.getMqttQos());
        mqttClient.publish(topic, Buffer.buffer(payload), qos, false, false);
    }

}
