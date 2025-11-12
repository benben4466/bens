package cn.ibenbeni.bens.iot.modular.base.service.rule.action;

import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleActionTypeEnum;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * IOT-场景规则-动作-设备控制场景动作
 */
@Slf4j
@Service
public class IotDeviceControlSceneRuleAction implements IotSceneRuleAction {

    @Override
    public void execute(IotDeviceMessage message, IotSceneRuleDO rule, IotSceneRuleDO.Action actionConfig) throws Exception {
    }

    @Override
    public IotSceneRuleActionTypeEnum getType() {
        return null;
    }

}
