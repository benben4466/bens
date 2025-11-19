package cn.ibenbeni.bens.iot.modular.base.mq.consumer.rule;

import cn.ibenbeni.bens.iot.modular.base.service.rule.IotSceneRuleService;
import cn.ibenbeni.bens.module.iot.core.messagebus.core.IotMessageBus;
import cn.ibenbeni.bens.module.iot.core.messagebus.core.IotMessageSubscriber;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * IOT-场景联动规则处理
 */
@Slf4j
@Component
public class IotSceneRuleMessageHandler implements IotMessageSubscriber<IotDeviceMessage> {

    @Resource
    private IotMessageBus messageBus;

    @Resource
    private IotSceneRuleService sceneRuleService;

    @PostConstruct
    public void init() {
        messageBus.register(this);
    }

    @Override
    public String getTopic() {
        return IotDeviceMessage.MESSAGE_BUS_DEVICE_MESSAGE_TOPIC;
    }

    @Override
    public String getGroup() {
        return "iot_scene_rule_consumer";
    }

    @Override
    public void onMessage(IotDeviceMessage message) {
        try {
            log.info("[onMessage][场景规则消息内容({})]", message);
            sceneRuleService.executeSceneRuleByDevice(message);
        } catch (Exception ex) {
            log.error("[onMessage][场景规则消息处理异常({})]", message, ex);
        }
    }

}
