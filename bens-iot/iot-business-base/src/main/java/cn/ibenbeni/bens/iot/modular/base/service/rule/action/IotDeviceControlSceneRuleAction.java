package cn.ibenbeni.bens.iot.modular.base.service.rule.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleActionTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotDeviceService;
import cn.ibenbeni.bens.iot.modular.base.service.device.message.IotDeviceMessageService;
import cn.ibenbeni.bens.module.iot.core.enums.IotDeviceMessageMethodEnum;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * IOT-场景规则-动作-设备控制场景动作
 * <p></p>
 */
@Slf4j
@Service
public class IotDeviceControlSceneRuleAction implements IotSceneRuleAction {

    @Resource
    private IotDeviceService deviceService;

    @Resource
    private IotDeviceMessageService deviceMessageService;

    @Override
    public void execute(IotDeviceMessage message, IotSceneRuleDO rule, IotSceneRuleDO.Action actionConfig) throws Exception {
        // 1.校验参数
        if (actionConfig.getDeviceId() == null) {
            log.error("[execute][规则场景({}) 动作配置({}) 设备编号不能为空]", rule.getId(), actionConfig);
            return;
        }
        if (StrUtil.isEmpty(actionConfig.getIdentifier())) {
            log.error("[execute][规则场景({}) 动作配置({}) 属性标识符不能为空]", rule.getId(), actionConfig);
            return;
        }

        // 2.判断是否为全部设备
        if (IotDeviceDO.DEVICE_ID_ALL.equals(actionConfig.getDeviceId())) {
            executeForAllDevices(rule, actionConfig);
        } else {
            executeForSingleDevice(rule, actionConfig);
        }

    }

    @Override
    public IotSceneRuleActionTypeEnum getType() {
        return IotSceneRuleActionTypeEnum.DEVICE_PROPERTY_SET;
    }

    /**
     * 产品下所有设备执行属性设置
     *
     * @param rule         场景规则
     * @param actionConfig 执行动作配置
     */
    private void executeForAllDevices(IotSceneRuleDO rule, IotSceneRuleDO.Action actionConfig) {
        // 参数校验
        if (actionConfig.getProductId() == null) {
            log.error("[executeForAllDevices][规则场景({}) 动作配置({}) 产品编号不能为空]", rule.getId(), actionConfig);
            return;
        }

        // 查询产品下所有设备
        List<IotDeviceDO> deviceList = deviceService.listDeviceByProductId(actionConfig.getProductId());
        if (CollUtil.isEmpty(deviceList)) {
            log.warn("[executeForAllDevices][规则场景({}) 动作配置({}) 产品({}) 下无设备]", rule.getId(), actionConfig, actionConfig.getProductId());
            return;
        }

        // 遍历设备执行属性设置
        for (IotDeviceDO device : deviceList) {
            executePropertySetForDevice(device, rule, actionConfig);
        }
    }

    /**
     * 单个设备执行属性设置
     *
     * @param rule         场景规则
     * @param actionConfig 执行动作配置
     */
    private void executeForSingleDevice(IotSceneRuleDO rule, IotSceneRuleDO.Action actionConfig) {
        // 获取设备信息
        IotDeviceDO device = deviceService.getDeviceFromCache(actionConfig.getDeviceId());
        if (device == null) {
            log.error("[executeForSingleDevice][规则场景({}) 动作配置({}) 对应的设备({}) 不存在]", rule.getId(), actionConfig, actionConfig.getDeviceId());
            return;
        }

        // 执行属性设置
        executePropertySetForDevice(device, rule, actionConfig);
    }

    /**
     * 设备执行属性设置
     *
     * @param device       设备
     * @param rule         场景规则
     * @param actionConfig 执行动作配置
     */
    private void executePropertySetForDevice(IotDeviceDO device, IotSceneRuleDO rule, IotSceneRuleDO.Action actionConfig) {
        // 构建属性设置消息
        IotDeviceMessage downstreamMessage = buildPropertySetMessage(actionConfig);
        if (downstreamMessage == null) {
            log.error("[executePropertySetForDevice][规则场景({}) 动作配置({}) 设备({}) 构建属性设置消息失败]", rule.getId(), actionConfig, device.getDeviceSn());
            return;
        }

        // 发送属性设置消息
        try {
            IotDeviceMessage result = deviceMessageService.sendDeviceMessage(downstreamMessage, device);
            log.info("[executePropertySetForDevice][规则场景({}) 动作配置({}) 设备({}) 属性设置消息({}) 发送成功]", rule.getId(), actionConfig, device.getDeviceSn(), result.getMsgId());
        } catch (Exception ex) {
            log.error("[executePropertySetForDevice][规则场景({}) 动作配置({}) 设备({}) 属性设置消息发送失败]", rule.getId(), actionConfig, device.getDeviceSn(), ex);
        }
    }

    private IotDeviceMessage buildPropertySetMessage(IotSceneRuleDO.Action actionConfig) {
        try {
            Object params = MapUtil.of("properties", MapUtil.of(actionConfig.getIdentifier(), actionConfig.getParams()));
            return IotDeviceMessage.requestOf(IotDeviceMessageMethodEnum.PROPERTY_SET.getMethod(), params);
        } catch (Exception ex) {
            log.error("[buildPropertySetMessage][构建属性设置消息异常]", ex);
            return null;
        }
    }

}
