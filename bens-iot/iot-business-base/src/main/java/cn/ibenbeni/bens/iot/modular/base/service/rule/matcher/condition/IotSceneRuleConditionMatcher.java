package cn.ibenbeni.bens.iot.modular.base.service.rule.matcher.condition;

import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleConditionTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.service.rule.matcher.IotSceneRuleMatcher;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;

/**
 * IOT-场景规则-条件匹配器接口
 * <p>作用：专门处理子条件的匹配逻辑。如设备状态、属性值、时间条件等</p>
 * <p>条件匹配器负责判断设备消息是否满足场景规则的附加条件，在触发器匹配成功后进行进一步的条件筛选</p>
 */
public interface IotSceneRuleConditionMatcher extends IotSceneRuleMatcher {

    /**
     * 获取支持的触发器类型
     */
    IotSceneRuleConditionTypeEnum getSupportedTriggerType();

    /**
     * 设备消息是否匹配该条件触发器
     * <p>判断设备消息是否满足条件触发器</p>
     *
     * @param message   设备消息
     * @param condition 场景规则条件触发器
     * @return 是否匹配
     */
    boolean matches(IotDeviceMessage message, IotSceneRuleDO.TriggerCondition condition);

}
