package cn.ibenbeni.bens.module.iot.gateway.protocol.emqx.router;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.iot.api.IotDeviceCommonApi;
import cn.ibenbeni.bens.iot.api.pojo.dto.device.IotDeviceAuthReqDTO;
import cn.ibenbeni.bens.iot.api.util.IotDeviceAuthUtils;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.gateway.service.device.message.IotDeviceMessageService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

/**
 * IoT-网关-EMQX 认证事件处理器
 * <p>
 * 为 EMQX 提供 HTTP 接口服务，包括：
 * 1. 设备认证接口 - 对应 EMQX HTTP 认证插件
 * 2. 设备事件处理接口 - 对应 EMQX Webhook 事件通知
 * </p>
 */
@Slf4j
public class IotEmqxAuthEventHandler {

    // region EMQX 认证和事件常量

    /**
     * 认证允许结果
     */
    private static final String RESULT_ALLOW = "allow";

    /**
     * 认证拒绝结果
     */
    private static final String RESULT_DENY = "deny";

    /**
     * 认证忽略结果
     */
    private static final String RESULT_IGNORE = "ignore";

    /**
     * EMQX 事件类型常量
     */
    private static final String EVENT_CLIENT_CONNECTED = "client.connected";
    private static final String EVENT_CLIENT_DISCONNECTED = "client.disconnected";

    // endregion

    /**
     * HTTP 成功状态码（EMQX 要求固定使用 200）
     */
    private static final int SUCCESS_STATUS_CODE = 200;

    private final String serverId;

    private final IotDeviceMessageService deviceMessageService;

    private final IotDeviceCommonApi deviceCommonApi;

    public IotEmqxAuthEventHandler(String serverId) {
        this.serverId = serverId;
        this.deviceMessageService = SpringUtil.getBean(IotDeviceMessageService.class);
        this.deviceCommonApi = SpringUtil.getBean(IotDeviceCommonApi.class);
    }

    /**
     * 处理EMQX认证事件
     *
     * @param context 路由上下文
     */
    public void handleAuth(RoutingContext context) {
        try {
            // 1.校验参数
            JsonObject bodyJo = parseRequestBody(context);
            if (bodyJo == null) {
                return;
            }
            String clientId = bodyJo.getString("clientid");
            String username = bodyJo.getString("username");
            String password = bodyJo.getString("password");
            log.debug("[handleAuth][设备认证请求: clientId={}, username={}]", clientId, username);
            if (StrUtil.hasBlank(clientId, username, password)) {
                log.info("[handleAuth][认证参数不完整: clientId={}, username={}]", clientId, username);
                sendAuthResponse(context, RESULT_DENY);
                return;
            }

            // 2.执行认证
            boolean authResult = handleDeviceAuth(clientId, username, password);
            log.info("[handleAuth][设备认证结果: {} -> {}]", username, authResult);
            if (authResult) {
                sendAuthResponse(context, RESULT_ALLOW);
            } else {
                sendAuthResponse(context, RESULT_DENY);
            }
        } catch (Exception ex) {
            log.error("[handleAuth][设备认证异常]", ex);
            sendAuthResponse(context, RESULT_IGNORE);
        }
    }

    /**
     * 处理 EMQX 事件
     * <p>WebHook服务注意快速返回结果</p>
     * <p>
     * 优化：
     * 1.可靠送达且快速：规则引擎 + MQ
     * </p>
     *
     * @param context 路由上下文
     */
    public void handleEvent(RoutingContext context) {
        JsonObject body = null;
        try {
            // 1.解析请求体
            body = parseRequestBody(context);
            if (body == null) {
                return;
            }
            String event = body.getString("event");
            String username = body.getString("username");
            log.debug("[handleEvent][收到事件: {} - {}]", event, username);

            // 2.根据事件类型进行分发处理
            switch (event) {
                case EVENT_CLIENT_CONNECTED:
                    handleClientConnected(body);
                    break;
                case EVENT_CLIENT_DISCONNECTED:
                    handleClientDisconnected(body);
                    break;
                default:
                    break;
            }

            // 3.返回结果。EMQX 事件 WebHook，只关心响应码，不关系 Body。
            context.response().setStatusCode(SUCCESS_STATUS_CODE).end();
        } catch (Exception ex) {
            log.error("[handleEvent][事件处理失败][body={}]", body != null ? body.encode() : "null", ex);
            // 即使处理失败，也返回 200 避免EMQX重试
            context.response().setStatusCode(SUCCESS_STATUS_CODE).end();
        }
    }

    /**
     * 解析请求体
     *
     * @param context 路由上下文
     * @return 请求体JSON对象，解析失败时返回null
     */
    private JsonObject parseRequestBody(RoutingContext context) {
        try {
            JsonObject bodyJo = context.body().asJsonObject();
            if (bodyJo == null) {
                log.info("[parseRequestBody][请求体为空]");
                sendAuthResponse(context, RESULT_IGNORE);
                return null;
            }

            return bodyJo;
        } catch (Exception ex) {
            log.error("[parseRequestBody][body({}) 解析请求体失败]", context.body().asString(), ex);
            sendAuthResponse(context, RESULT_IGNORE);
            return null;
        }
    }

    /**
     * 发送 EMQX 认证响应
     *
     * @param context 路由上下文
     * @param result  认证结果：allow、deny、ignore
     */
    private void sendAuthResponse(RoutingContext context, String result) {
        // 构建符合 EMQX 官方规范的响应
        JsonObject response = new JsonObject()
                .put("result", result) // 认证结果
                .put("is_superuser", false); //是否是超级用户

        context.response()
                .setStatusCode(SUCCESS_STATUS_CODE)
                .putHeader("Content-Type", "application/json; charset=utf-8")
                .end(response.encode()); // JsonObject#encode()：转为JSON格式字符串
    }

    /**
     * 处理设备认证
     *
     * @param clientId 客户端ID
     * @param username 用户名
     * @param password 密码
     * @return 认证是否成功
     */
    private boolean handleDeviceAuth(String clientId, String username, String password) {
        try {
            Boolean result = deviceCommonApi.authDevice(new IotDeviceAuthReqDTO(clientId, username, password));
            return BooleanUtil.isTrue(result);
        } catch (Exception ex) {
            log.error("[handleDeviceAuth][设备({}) 认证接口调用失败]", username, ex);
            throw ex;
        }
    }

    /**
     * 处理 EMQX 客户端连接事件
     *
     * @param body JSON对象
     */
    private void handleClientConnected(JsonObject body) {
        String username = body.getString("username");
        log.info("[handleClientConnected][设备上线: {}]", username);
        handleDeviceStateChange(username, true);
    }

    /**
     * 处理 EMQX 客户端断开事件
     *
     * @param body JSON对象
     */
    private void handleClientDisconnected(JsonObject body) {
        String username = body.getString("username");
        String reason = body.getString("reason");
        log.info("[handleClientDisconnected][设备下线: {} ({})]", username, reason);
        handleDeviceStateChange(username, false);
    }

    /**
     * 处理设备状态变更
     *
     * @param username 用户名
     * @param online   是否在线；true=在线；false=离线；
     */
    private void handleDeviceStateChange(String username, boolean online) {
        // 1.解析连接名称
        IotDeviceAuthUtils.DeviceInfo deviceInfo = IotDeviceAuthUtils.parseUsername(username);
        if (deviceInfo == null) {
            log.debug("[handleDeviceStateChange][跳过非设备({})连接]", username);
            return;
        }

        try {
            // 2.构建设备消息-状态
            IotDeviceMessage message = online ? IotDeviceMessage.buildStateUpdateOnline() : IotDeviceMessage.buildStateUpdateOffline();

            // 3.发送设备消息
            deviceMessageService.sendDeviceMessage(message, deviceInfo.getProductKey(), deviceInfo.getDeviceSn(), serverId);
        } catch (Exception ex) {
            log.error("[handleDeviceStateChange][发送设备状态消息失败: {}]", username, ex);
        }
    }

}
