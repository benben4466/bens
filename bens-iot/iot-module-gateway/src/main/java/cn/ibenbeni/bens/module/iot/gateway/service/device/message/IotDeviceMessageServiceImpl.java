package cn.ibenbeni.bens.module.iot.gateway.service.device.message;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.iot.api.IotDeviceCommonApi;
import cn.ibenbeni.bens.iot.api.exception.IotException;
import cn.ibenbeni.bens.iot.api.exception.enums.IotExceptionEnum;
import cn.ibenbeni.bens.iot.api.pojo.dto.device.IotDeviceRespDTO;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.core.mq.producer.IotDeviceMessageProducer;
import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import cn.ibenbeni.bens.module.iot.gateway.codec.IotDeviceMessageCodec;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.rule.util.TimestampUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * IOT-设备消息-服务实现类
 * <p>网关</p>
 */
@Slf4j
@Service(IotDeviceMessageServiceImpl.GATEWAY_DEVICE_MESSAGE_SERVICE_NAME)
public class IotDeviceMessageServiceImpl implements IotDeviceMessageService {

    public final static String GATEWAY_DEVICE_MESSAGE_SERVICE_NAME = "gatewayDeviceMessageService";

    private final Map<String, IotDeviceMessageCodec> codes;

    @Resource
    private IotDeviceMessageProducer deviceMessageProducer;

    @Resource
    private IotDeviceCommonApi deviceCommonApi;

    public IotDeviceMessageServiceImpl(List<IotDeviceMessageCodec> codes) {
        this.codes = CollectionUtils.convertMap(codes, IotDeviceMessageCodec::type);
    }

    @Override
    public byte[] encodeDeviceMessage(IotDeviceMessage message, String productKey, String deviceSn) {
        return new byte[0];
    }

    @Override
    public IotDeviceMessage decodeDeviceMessage(byte[] bytes, String productKey, String deviceSn) {
        // 1.获取设备信息
        IotDeviceRespDTO device = deviceCommonApi.getDevice(productKey, deviceSn);
        if (device == null) {
            throw new IotException(IotExceptionEnum.DEVICE_NOT_EXISTED);
        }

        // 2.获取编解码器
        IotDeviceMessageCodec codec = codes.get(device.getDataFormat());
        if (codec == null) {
            log.error("[decodeDeviceMessage][设备({}/{})缺失编码器({})]", device.getProductKey(), device.getDeviceSn(), device.getDataFormat());
            throw new IotException(IotExceptionEnum.MSG_CODEC_NOT_EXISTED);
        }

        // 3.解码消息
        return codec.decode(bytes);
    }

    @Override
    public void sendDeviceMessage(IotDeviceMessage message, String productKey, String deviceSn, String serverId) {
        // 获取设备信息
        IotDeviceRespDTO device = deviceCommonApi.getDevice(productKey, deviceSn);
        if (device == null) {
            log.error("[sendDeviceMessage]设备({}/{})不存在", productKey, deviceSn);
            throw new IotException(IotExceptionEnum.DEVICE_NOT_EXISTED);
        }

        // 补充信息
        appendDeviceMessage(message, device, serverId);
        // 发送消息
        deviceMessageProducer.sendDeviceMessage(message);
    }

    /**
     * 补充消息的后端字段
     *
     * @param message  消息
     * @param device   设备信息
     * @param serverId 设备连接的 serverId
     */
    private void appendDeviceMessage(IotDeviceMessage message, IotDeviceRespDTO device, String serverId) {
        message.setMsgId(IotDeviceMessageUtils.generateMsgId());
        message.setReportTime(TimestampUtils.curUtcMillis());
        message.setDeviceId(device.getDeviceId());
        message.setTenantId(device.getTenantId());
        message.setServerId(serverId);
        // 特殊：如果设备没有指定 requestId，则使用 messageId
        if (StrUtil.isEmpty(message.getRequestId())) {
            message.setRequestId(message.getMsgId());
        }
    }

}
