package cn.ibenbeni.bens.iot.modular.base.entity.device;

import cn.ibenbeni.bens.module.iot.core.enums.IotDeviceMessageMethodEnum;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IOT-设备消息-实体
 * <p>存储位置: TDengine</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotDeviceMessageDO {

    /**
     * 消息ID
     */
    private String msgId;

    /**
     * 上报时间戳
     */
    private Long reportTime;

    /**
     * 存储时间戳
     */
    private Long ts;

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 服务编号
     * <p>该消息由哪个 server 发送</p>
     */
    private String serverId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 是否上行消息
     * <p>目的：方便统计上下行消息数量</p>
     * <p>由 {@link IotDeviceMessageUtils#isUpstreamMessage(IotDeviceMessage)} 计算</p>
     */
    private Boolean upstream;

    /**
     * 是否回复消息
     * <p>目的：方便计算请求/回复</p>
     */
    private Boolean reply;

    /**
     * 标识符
     * <p>目前：只有事件上报、服务调用才有！！！</p>
     */
    private String identifier;

    /**
     * 请求ID
     * <p>由设备生成</p>
     */
    private String requestId;

    /**
     * 请求方法
     * <p>枚举：{@link IotDeviceMessageMethodEnum}</p>
     */
    private String method;

    /**
     * 请求参数
     * <p>例如说：属性上报的 properties、事件上报的 params</p>
     */
    private Object params;

    /**
     * 响应结果
     */
    private Object data;

    /**
     * 响应错误码
     */
    private Integer code;

    /**
     * 响应提示
     */
    private String msg;

}
