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
 * IOT-场景规则-设备状态更新触发器匹配器
 */
@Slf4j
@Component
public class IotDeviceStateUpdateTriggerMatcher implements IotSceneRuleTriggerMatcher {

    @Override
    public IotSceneRuleTriggerTypeEnum getSupportedTriggerType() {
        return IotSceneRuleTriggerTypeEnum.DEVICE_STATE_UPDATE;
    }

    @Override
    public boolean matches(IotDeviceMessage message, IotSceneRuleDO.Trigger trigger) {
        // 校验触发器是否为空、类型是否为空
        if (trigger == null || trigger.getType() == null) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 触发器为空或者类型为空]", message, trigger);
            return false;
        }

        // 校验触发器类型是否为设备状态更新
        if (!IotSceneRuleTriggerTypeEnum.DEVICE_STATE_UPDATE.getType().equals(trigger.getType())) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 触发器类型不正确, 期望类型: {}, 实际类型: {}]", message, trigger, IotSceneRuleTriggerTypeEnum.DEVICE_STATE_UPDATE.getType(), trigger.getType());
            return false;
        }

        // 校验操作符和参数
        if (trigger.getOperator() == null || trigger.getParam() == null) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 操作符或值为空]", message, trigger);
            return false;
        }

        // 获取设备状态值
        String statusVal = IotDeviceMessageUtils.getIdentifier(message);
        if (StrUtil.isBlank(statusVal)) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 设备状态值为空]", message, trigger);
            return false;
        }

        // 计算 EL 表达式是否匹配
        boolean isMatch = IotSceneRuleMatcherHelper.evaluateELCondition(trigger.getOperator(), statusVal, trigger.getParam());
        if (isMatch) {
            log.info("[matches][消息: {}, 触发器: {}, 匹配成功]", message, trigger);
        } else {
            log.info("[matches][消息: {}, 触发器: {}, 原因: 匹配失败,触发器条件未满足]", message, trigger);
        }

        return isMatch;
    }

    @Override
    public int getPriority() {
        return 10;
    }

}
