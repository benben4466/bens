package cn.ibenbeni.bens.module.iot.core.mq.message;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.iot.api.enums.device.IotDeviceStateEnum;
import cn.ibenbeni.bens.module.iot.core.enums.IotDeviceMessageMethodEnum;
import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import cn.ibenbeni.bens.rule.constants.RuleConstants;
import cn.ibenbeni.bens.rule.util.TimestampUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * IOT-设备消息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotDeviceMessage {

    /**
     * 【消息总线】应用的设备消息 Topic，由 iot-gateway 发给 iot-biz 进行消费
     */
    public static final String MESSAGE_BUS_DEVICE_MESSAGE_TOPIC = "iot_device_message";

    /**
     * 【消息总线】设备消息 Topic，由 iot-biz 发送给 iot-gateway 的某个 "server"(protocol) 进行消费
     *
     * <p>其中，%s 就是该"server"(protocol) 的标识</p>
     */
    public static final String MESSAGE_BUS_GATEWAY_DEVICE_MESSAGE_TOPIC = MESSAGE_BUS_DEVICE_MESSAGE_TOPIC + "_%s";

    /**
     * 消息ID
     * <p>由{@link IotDeviceMessageUtils#generateMsgId()}生成</p>
     * <p>填充方：后端服务</p>
     */
    private String msgId;

    /**
     * 上报时间戳
     * <p>填充方：后端服务</p>
     */
    private Long reportTime;

    /**
     * 设备ID
     * <p>填充方：后端服务</p>
     */
    private Long deviceId;

    /**
     * 租户ID
     * <p>填充方：后端服务</p>
     */
    private Long tenantId;

    /**
     * 服务编号
     * <p>该消息由哪个 server 发送</p>
     */
    private String serverId;

    /**
     * 请求ID
     * <p>由设备生成</p>
     * <p>若设备未生成，则 后端服务填充消息ID</p>
     */
    private String requestId;

    /**
     * 请求方法
     * <p>枚举: </p>
     */
    private String method;

    /**
     * 请求参数
     * <p>如：属性上报的 properties；事件上报的 params</p>
     */
    private Object params;

    /**
     * 响应结果
     */
    private Object data;

    /**
     * 响应错误码
     */
    private String code;

    /**
     * 返回结果信息
     */
    private String msg;

    // region 方法

    public static IotDeviceMessage requestOf(String method, Object params) {
        return requestOf(null, method, params);
    }

    public static IotDeviceMessage requestOf(String requestId, String method, Object params) {
        return of(null, requestId, method, params, null, null, null);
    }

    public static IotDeviceMessage replyOf(String requestId, String method, Object data, String code, String msg) {
        if (code == null) {
            code = RuleConstants.SUCCESS_CODE;
            msg = RuleConstants.SUCCESS_MESSAGE;
        }

        return of(null, requestId, method, null, data, code, msg);
    }

    public static IotDeviceMessage replyOf(String msgId, String requestId, String method, Object params, Object data, String code, String msg) {
        if (code == null) {
            code = RuleConstants.SUCCESS_CODE;
            msg = RuleConstants.SUCCESS_MESSAGE;
        }

        return of(msgId, requestId, method, params, data, code, msg);
    }

    public static IotDeviceMessage of(String msgId, String requestId, String method, Object params, Object data, String code, String msg) {
        if (StrUtil.isBlank(msgId)) {
            msgId = IotDeviceMessageUtils.generateMsgId();
        }

        return IotDeviceMessage.builder()
                .msgId(msgId)
                .reportTime(TimestampUtils.curUtcMillis())
                .requestId(requestId)
                .method(method)
                .params(params)
                .data(data)
                .code(code)
                .msg(msg)
                .build();
    }

    /**
     * 构建设备状态-在线设备消息
     */
    public static IotDeviceMessage buildStateUpdateOnline() {
        Map<String, Integer> paramMap = MapUtil.of("state", IotDeviceStateEnum.ONLINE.getState());
        return requestOf(IotDeviceMessageMethodEnum.STATE_UPDATE.getMethod(), paramMap);
    }

    /**
     * 构建设备状态-离线设备消息
     */
    public static IotDeviceMessage buildStateUpdateOffline() {
        Map<String, Integer> paramMap = MapUtil.of("state", IotDeviceStateEnum.OFFLINE.getState());
        return requestOf(IotDeviceMessageMethodEnum.STATE_UPDATE.getMethod(), paramMap);
    }

    // endregion

}
