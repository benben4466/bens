package cn.ibenbeni.bens.iot.modular.base.service.rule.matcher.trigger;

import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleTriggerTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.service.rule.matcher.IotSceneRuleMatcher;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;

/**
 * IOT-场景规则-触发器匹配器
 * <p>作用：专门处理主触发条件的匹配逻辑。如设备消息类型、定时器等。</p>
 * <p>触发器匹配器负责判断设备消息是否满足场景规则的主触发条件，是场景规则执行的第一道门槛</p>
 */
public interface IotSceneRuleTriggerMatcher extends IotSceneRuleMatcher {

    /**
     * 获取支持的触发器类型
     */
    IotSceneRuleTriggerTypeEnum getSupportedTriggerType();

    /**
     * 设备消息是否匹配该触发器
     * <p>判断设备消息是否满足触发器条件</p>
     *
     * @param message 设备消息
     * @param trigger 场景规则触发器
     * @return 是否匹配
     */
    boolean matches(IotDeviceMessage message, IotSceneRuleDO.Trigger trigger);

}
