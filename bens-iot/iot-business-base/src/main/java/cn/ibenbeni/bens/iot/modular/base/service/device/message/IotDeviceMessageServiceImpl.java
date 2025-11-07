package cn.ibenbeni.bens.iot.modular.base.service.device.message;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.api.exception.IotException;
import cn.ibenbeni.bens.iot.api.exception.enums.IotExceptionEnum;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceMessageDO;
import cn.ibenbeni.bens.iot.modular.base.mapper.tdengine.device.IotDeviceMessageMapper;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.device.IotDeviceMessagePageReq;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotDeviceService;
import cn.ibenbeni.bens.iot.modular.base.service.device.property.IotDevicePropertyService;
import cn.ibenbeni.bens.module.iot.core.enums.IotDeviceMessageMethodEnum;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.core.mq.producer.IotDeviceMessageProducer;
import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.rule.util.JsonUtils;
import cn.ibenbeni.bens.rule.util.TimestampUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * IOT设备消息-服务实现类
 */
@Slf4j
@Validated
@Service
public class IotDeviceMessageServiceImpl implements IotDeviceMessageService {

    @Resource
    private IotDeviceMessageProducer deviceMessageProducer;

    @Resource
    private IotDeviceMessageMapper deviceMessageMapper;

    @Resource
    private IotDeviceService deviceService;

    @Resource
    private IotDevicePropertyService devicePropertyService;

    @Override
    public void defineDeviceMessageStable() {
        if (StrUtil.isNotBlank(deviceMessageMapper.showSTable())) {
            log.info("[defineDeviceMessageStable][设备消息超级表已存在, 创建跳过]");
            return;
        }
        log.info("[defineDeviceMessageStable][设备消息超级表不存在, 创建开始]");
        deviceMessageMapper.createSTable();
        log.info("[defineDeviceMessageStable][设备消息超级表不存在, 创建成功]");
    }

    @Override
    public IotDeviceMessage sendDeviceMessage(IotDeviceMessage message) {
        IotDeviceDO device = deviceService.validateDeviceExists(message.getDeviceId());
        return sendDeviceMessage(message, device);
    }

    @Override
    public IotDeviceMessage sendDeviceMessage(IotDeviceMessage message, IotDeviceDO device) {
        return sendDeviceMessage(message, device, null);
    }

    @Override
    public void handleUpstreamDeviceMessage(IotDeviceMessage message, IotDeviceDO device) {
        // 处理设备上行消息，并拿到回复数据
        Object replyData = null;
        ServiceException serviceException = null;
        try {
            replyData = doHandleUpstreamDeviceMessage(message, device);
        } catch (ServiceException serviceEx) {
            serviceException = serviceEx;
            log.error("[handleUpstreamDeviceMessage][message({}) 业务异常]", message, serviceException);
        } catch (Exception ex) {
            log.error("[handleUpstreamDeviceMessage][message({}) 发生异常]", message, ex);
            throw ex;
        }

        // 记录设备消息
        createDeviceMessageLog(message);

        // 非回复消息，或者禁用回复消息，或者无服务编号，则不回复消息
        if (IotDeviceMessageUtils.isReplyMessage(message)
                || IotDeviceMessageMethodEnum.isReplyDisabled(message.getMethod())
                || StrUtil.isEmpty(message.getServerId())) {
            return;
        }
        try {
            // 构建 ACK 消息，并且发送 ACK 消息
            IotDeviceMessage replyMessage = IotDeviceMessage.replyOf(message.getRequestId(), message.getMethod(), replyData,
                    serviceException != null ? serviceException.getErrorCode() : null,
                    serviceException != null ? serviceException.getUserTip() : null
            );
            sendDeviceMessage(replyMessage, device, message.getServerId());
        } catch (Exception ex) {
            log.error("[handleUpstreamDeviceMessage][message({}) 回复消息失败]", message, ex);
        }
    }

    @Override
    public PageResult<IotDeviceMessageDO> pageDeviceMessage(IotDeviceMessagePageReq pageReq) {
        try {
            long qStartTs = -1L;
            long qEndTs = -1L;
            if (pageReq.getTimes() != null && pageReq.getTimes().length == 2) {
                qStartTs = TimestampUtils.toMillis(pageReq.getTimes()[0]);
                qEndTs = TimestampUtils.toMillis(pageReq.getTimes()[1]);
            }

            IPage<IotDeviceMessageDO> page = deviceMessageMapper.selectPage(new Page<>(pageReq.getPageNo(), pageReq.getPageSize()), pageReq, qStartTs, qEndTs);
            return PageResultFactory.createPageResult(page.getRecords(), page.getTotal(), pageReq.getPageSize(), pageReq.getPageNo());
        } catch (Exception ex) {
            // 设备消息表不存在，则返回空结果
            if (ex.getMessage().contains("Table does not exist")) {
                return PageResultFactory.createEmptyPageResult();
            }

            throw ex;
        }
    }

    /**
     * 处理设备消息
     *
     * @param message 设备消息
     * @param device  设备
     * @return 回复数据
     */
    private Object doHandleUpstreamDeviceMessage(IotDeviceMessage message, IotDeviceDO device) {
        // 设备上下线
        if (ObjectUtil.equal(message.getMethod(), IotDeviceMessageMethodEnum.STATE_UPDATE.getMethod())) {
            String stateStr = IotDeviceMessageUtils.getIdentifier(message);
            assert stateStr != null;
            Assert.notEmpty(stateStr, "设备状态不能为空");
            deviceService.updateDeviceState(device, Integer.valueOf(stateStr));
            return null;
        }

        // 属性上报
        if (ObjectUtil.equal(message.getMethod(), IotDeviceMessageMethodEnum.PROPERTY_POST.getMethod())) {
            devicePropertyService.saveDeviceProperty(device, message);
        }

        return null;
    }

    /**
     * 向发送设备消息
     *
     * @param message  设备消息
     * @param device   设备
     * @param serverId 服务编号
     * @return 设备消息
     */
    private IotDeviceMessage sendDeviceMessage(IotDeviceMessage message, IotDeviceDO device, String serverId) {
        // 补充信息
        appendDeviceMessage(message, device);

        // 情况1：发送上行消息
        boolean upstream = IotDeviceMessageUtils.isUpstreamMessage(message);
        if (upstream) {
            deviceMessageProducer.sendDeviceMessage(message);
            return message;
        }

        // 情况2：发送下行消息
        if (StrUtil.isEmpty(serverId)) {
            serverId = devicePropertyService.getDeviceServerId(device.getDeviceId());
            if (StrUtil.isEmpty(serverId)) {
                throw new IotException(IotExceptionEnum.DEVICE_DOWNSTREAM_FAILED_SERVER_ID_NULL);
            }
        }
        // 发送消息
        deviceMessageProducer.sendDeviceMessageToGateway(serverId, message);
        // 记录消息日志。原因：上行消息，消费时，已经会记录；下行消息，因为消费在 Gateway 端，所以需要在这里记录
        createDeviceMessageLog(message);
        return message;
    }

    /**
     * 补充消息的后端属性
     *
     * @param message 设备消息
     * @param device  设备
     */
    private void appendDeviceMessage(IotDeviceMessage message, IotDeviceDO device) {
        message.setMsgId(IotDeviceMessageUtils.generateMsgId()); // 填充消息ID
        message.setReportTime(TimestampUtils.curUtcMillis()); // 填充上报时间
        message.setDeviceId(device.getDeviceId()); // 填充设备ID
        message.setTenantId(device.getTenantId()); // 填充租户ID
        // 特殊:如果未指定 RequestI，则默认使用消息ID
        if (StrUtil.isBlank(message.getRequestId())) {
            message.setRequestId(message.getMsgId());
        }
    }

    /**
     * 创建设备消息日志
     *
     * @param message 设备消息
     */
    private void createDeviceMessageLog(IotDeviceMessage message) {
        IotDeviceMessageDO messageDO = BeanUtil.toBean(message, IotDeviceMessageDO.class);
        messageDO.setUpstream(IotDeviceMessageUtils.isUpstreamMessage(message));
        messageDO.setReply(IotDeviceMessageUtils.isReplyMessage(message));
        messageDO.setIdentifier(IotDeviceMessageUtils.getIdentifier(message));

        if (message.getParams() != null) {
            messageDO.setParams(JsonUtils.toJsonStr(message.getParams()));
        }
        if (message.getData() != null) {
            messageDO.setData(JsonUtils.toJsonStr(message.getData()));
        }
        // 入库
        deviceMessageMapper.insert(messageDO);
    }

}
