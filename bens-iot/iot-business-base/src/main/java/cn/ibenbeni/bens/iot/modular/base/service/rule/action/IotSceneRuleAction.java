package cn.ibenbeni.bens.iot.modular.base.service.rule.action;

import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleActionTypeEnum;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;

/**
 * 场景联动规则-执行器接口
 */
public interface IotSceneRuleAction {

    /**
     * 执行场景联动规则
     *
     * @param message      设备消息；[若为空，定时触发；若非空，设备触发；]
     * @param rule         场景规则
     * @param actionConfig 执行动作配置
     * @throws Exception 抛出异常
     */
    void execute(IotDeviceMessage message, IotSceneRuleDO rule, IotSceneRuleDO.Action actionConfig) throws Exception;

    /**
     * @return 执行器类型
     */
    IotSceneRuleActionTypeEnum getType();

}
