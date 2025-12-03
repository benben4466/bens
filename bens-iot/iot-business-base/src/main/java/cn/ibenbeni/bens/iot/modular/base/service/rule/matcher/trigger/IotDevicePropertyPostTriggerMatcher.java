package cn.ibenbeni.bens.iot.modular.base.service.rule.matcher.trigger;

import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleTriggerTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.service.rule.matcher.IotSceneRuleMatcherHelper;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * IOT-场景规则-设备属性上报触发器匹配器
 */
@Slf4j
@Component
public class IotDevicePropertyPostTriggerMatcher implements IotSceneRuleTriggerMatcher {

    @Override
    public IotSceneRuleTriggerTypeEnum getSupportedTriggerType() {
        return IotSceneRuleTriggerTypeEnum.DEVICE_PROPERTY_POST;
    }

    @Override
    public boolean matches(IotDeviceMessage message, IotSceneRuleDO.Trigger trigger) {
        if (trigger == null || trigger.getType() == null) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 触发器为空或者类型为空]", message, trigger);
            return false;
        }

        // 校验触发器类型是否为设备属性上报
        if (!IotSceneRuleTriggerTypeEnum.DEVICE_PROPERTY_POST.getType().equals(trigger.getType())) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 触发器类型不正确, 期望类型: {}, 实际类型: {}]", message, trigger, IotSceneRuleTriggerTypeEnum.DEVICE_PROPERTY_POST.getType(), trigger.getType());
            return false;
        }

        if (trigger.getOperator() == null || trigger.getParam() == null) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 操作符或值为空]", message, trigger);
            return false;
        }

        // 获取消息的属性值
        Object propertyValue = IotDeviceMessageUtils.extractPropertyValue(message, trigger.getIdentifier());

        // 计算 EL 表达式是否匹配
        boolean isMatch = IotSceneRuleMatcherHelper.evaluateELCondition(trigger.getOperator(), propertyValue, trigger.getParam());
        if (isMatch) {
            log.info("[matches][消息: {}, 触发器: {}, 匹配成功]", message, trigger);
        } else {
            log.info("[matches][消息: {}, 触发器: {}, 原因: 匹配失败,触发器条件未满足]", message, trigger);
        }

        return isMatch;
    }

    @Override
    public int getPriority() {
        return 20;
    }

}
