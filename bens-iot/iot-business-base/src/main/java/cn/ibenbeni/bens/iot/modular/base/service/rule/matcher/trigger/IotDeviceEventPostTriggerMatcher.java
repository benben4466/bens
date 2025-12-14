package cn.ibenbeni.bens.iot.modular.base.service.rule.matcher.trigger;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleTriggerTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.service.rule.matcher.IotSceneRuleMatcherHelper;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * IOT-场景规则-设备事件上报触发器匹配器
 */
@Slf4j
@Component
public class IotDeviceEventPostTriggerMatcher implements IotSceneRuleTriggerMatcher {

    @Override
    public IotSceneRuleTriggerTypeEnum getSupportedTriggerType() {
        return IotSceneRuleTriggerTypeEnum.DEVICE_EVENT_POST;
    }

    @Override
    public boolean matches(IotDeviceMessage message, IotSceneRuleDO.Trigger trigger) {
        if (trigger == null || trigger.getType() == null) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 触发器为空或者类型为空]", message, trigger);
            return false;
        }

        // 校验触发器类型是否为设备事件上报
        if (!IotSceneRuleTriggerTypeEnum.DEVICE_EVENT_POST.getType().equals(trigger.getType())) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 触发器类型不正确, 期望类型: {}, 实际类型: {}]", message, trigger, IotSceneRuleTriggerTypeEnum.DEVICE_EVENT_POST.getName(), trigger.getType());
            return false;
        }

        if (trigger.getOperator() == null || trigger.getParam() == null) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 操作符或值为空]", message, trigger);
            return false;
        }

        // 消息的属性标识符
        String msgIdentifier = IotDeviceMessageUtils.getIdentifier(message);
        if (IotSceneRuleMatcherHelper.isIdentifierMatched(trigger.getIdentifier(), msgIdentifier)) {
            log.error("[matches][消息: {}, 触发器: {}, 原因: 消息的属性标识符不匹配]", message, trigger);
            return false;
        }

        // TODO 检查该设备是否有此事件

        /**
         * 事件触发：
         * 1.事件标识符匹配成功，则触发事件触发器。
         * 2.需要匹配具体的值，则匹配事件上报的值和触发器定义的值是否一致。
         */
        if (StrUtil.isNotBlank(trigger.getOperator()) && StrUtil.isNotBlank(trigger.getParam())) {
            Map<String, Object> paramsMap = BeanUtil.beanToMap(message.getParams());
            // 从中提取 value 下的数据
            if (CollUtil.isEmpty(paramsMap) || ObjectUtil.isEmpty(paramsMap.get("value"))) {
                log.info("[matches][消息: {}, 触发器: {}, 原因: 消息事件中数据为空]", message, trigger);
                return false;
            }

            boolean matched = IotSceneRuleMatcherHelper.evaluateELCondition(trigger.getOperator(), paramsMap.get("value"), trigger.getParam());
            if (!matched) {
                log.info("[matches][匹配触发器失败][消息: {}, 触发器: {}, 原因: 事件数据条件不匹配]", message, trigger);
                return false;
            }
        }

        log.info("[matches][匹配触发器成功][消息: {}, 触发器: {}]", message, trigger);
        return false;
    }

    @Override
    public int getPriority() {
        return 30;
    }

}
