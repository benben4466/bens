package cn.ibenbeni.bens.iot.modular.base.service.rule.matcher;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleConditionTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleTriggerTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.service.rule.matcher.condition.IotSceneRuleConditionMatcher;
import cn.ibenbeni.bens.iot.modular.base.service.rule.matcher.trigger.IotSceneRuleTriggerMatcher;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * IOT-场景规则匹配器管理器
 * <p>作用：管理场景规则匹配器（触发器匹配器和条件匹配器）</p>
 */
@Slf4j
@Component
public class IotSceneRuleMatcherManager {

    /**
     * 触发器匹配器映射
     */
    private final Map<IotSceneRuleTriggerTypeEnum, IotSceneRuleTriggerMatcher> triggerMatchers;

    /**
     * 条件匹配器映射表
     */
    private final Map<IotSceneRuleConditionTypeEnum, IotSceneRuleConditionMatcher> conditionMatchers;

    public IotSceneRuleMatcherManager(List<IotSceneRuleMatcher> matchers) {
        if (CollUtil.isEmpty(matchers)) {
            log.warn("[IotSceneRuleMatcherManager][无任何场景规则匹配器]");
            this.triggerMatchers = new HashMap<>();
            this.conditionMatchers = new HashMap<>();
            return;
        }

        // 获取所有启用的匹配器 并根据优先级排序
        List<IotSceneRuleMatcher> allMatchers = matchers.stream()
                .filter(IotSceneRuleMatcher::isEnabled)
                .sorted(Comparator.comparing(IotSceneRuleMatcher::getPriority))
                .collect(Collectors.toList());

        // 获取触发器匹配器
        List<IotSceneRuleTriggerMatcher> triggerMatchers = allMatchers.stream()
                .filter(matcher -> matcher instanceof IotSceneRuleTriggerMatcher)
                .map(matcher -> (IotSceneRuleTriggerMatcher) matcher)
                .collect(Collectors.toList());

        // 获取条件匹配器
        List<IotSceneRuleConditionMatcher> conditionMatchers = allMatchers.stream()
                .filter(matcher -> matcher instanceof IotSceneRuleConditionMatcher)
                .map(matcher -> (IotSceneRuleConditionMatcher) matcher)
                .collect(Collectors.toList());

        // 构建触发器匹配器映射
        this.triggerMatchers = CollectionUtils.convertMap(
                triggerMatchers,
                IotSceneRuleTriggerMatcher::getSupportedTriggerType,
                Function.identity(),
                (existing, replacement) -> {
                    boolean existingIsPriority = existing.getPriority() <= replacement.getPriority();
                    log.warn("[IotSceneRuleMatcherManager][触发器类型({})存在多个匹配器, 使用优先级更高的: {}]", existing.getSupportedTriggerType(), existingIsPriority ? existing.getClass().getName() : replacement.getClass().getName());
                    return existingIsPriority ? existing : replacement;
                },
                LinkedHashMap::new
        );

        // 构建条件触发器匹配器映射
        this.conditionMatchers = CollectionUtils.convertMap(
                conditionMatchers,
                IotSceneRuleConditionMatcher::getSupportedTriggerType,
                Function.identity(),
                (existing, replacement) -> {
                    boolean existingIsPriority = existing.getPriority() <= replacement.getPriority();
                    log.warn("[IotSceneRuleMatcherManager][条件类型({})存在多个匹配器, 使用优先级更高的: {}]", existing.getSupportedTriggerType(), existingIsPriority ? existing.getClass().getName() : replacement.getClass().getName());
                    return existingIsPriority ? existing : replacement;
                },
                LinkedHashMap::new
        );

        // 日志
        log.info("[IotSceneRuleMatcherManager][初始化完成, 共加载({})个匹配器, 其中触发器匹配器({})个, 条件匹配器({})个]", allMatchers.size(), triggerMatchers.size(), conditionMatchers.size());
        this.triggerMatchers.forEach((type, matcher) -> {
            log.info("[IotSceneRuleMatcherManager][触发器匹配器类型: ({}), 优先级: ({})]", type, matcher.getPriority());
        });
        this.conditionMatchers.forEach((type, matcher) -> {
            log.info("[IotSceneRuleMatcherManager][条件匹配器类型: ({}), 优先级: ({})]", type, matcher.getPriority());
        });
    }

    /**
     * 检查设备消息是否匹配该触发器
     *
     * @param message 设备消息
     * @param trigger 场景规则触发器
     * @return 是否匹配
     */
    public boolean isMatched(IotDeviceMessage message, IotSceneRuleDO.Trigger trigger) {
        if (ObjectUtil.hasEmpty(message, trigger, trigger.getType())) {
            log.debug("[isMatched][message({}) trigger({}) 参数无效]", message, trigger);
            return false;
        }

        // 触发器类型
        IotSceneRuleTriggerTypeEnum triggerType = IotSceneRuleTriggerTypeEnum.typeOf(trigger.getType());
        if (triggerType == null) {
            log.warn("[isMatched][triggerType({}) 未知的触发器类型]", trigger.getType());
            return false;
        }
        // 获取触发器匹配器
        IotSceneRuleTriggerMatcher matcher = triggerMatchers.get(triggerType);
        if (matcher == null) {
            log.warn("[isMatched][triggerType({}) 没有对应的匹配器]", triggerType);
            return false;
        }

        try {
            return matcher.matches(message, trigger);
        } catch (Exception ex) {
            log.error("[isMatched][触发器匹配异常] message: {}, trigger: {}", message, trigger, ex);
            return false;
        }
    }

    /**
     * 检查设备消息是否匹配该条件触发器
     *
     * @param message   设备消息
     * @param condition 场景规则条件触发器
     * @return 是否匹配
     */
    public boolean isConditionMatched(IotDeviceMessage message, IotSceneRuleDO.TriggerCondition condition) {
        if (ObjectUtil.hasEmpty(message, condition, condition.getType())) {
            log.debug("[isConditionMatched][message({}) condition({}) 参数无效]", message, condition);
            return false;
        }

        // 获取条件匹配器类型
        IotSceneRuleConditionTypeEnum conditionType = IotSceneRuleConditionTypeEnum.typeOf(condition.getType());
        if (conditionType == null) {
            log.warn("[isConditionMatched][conditionType({}) 未知的条件类型]", condition.getType());
            return false;
        }
        IotSceneRuleConditionMatcher matcher = conditionMatchers.get(conditionType);
        if (matcher == null) {
            log.warn("[isConditionMatched][conditionType({}) 没有对应的匹配器]", conditionType);
            return false;
        }

        try {
            return matcher.matches(message, condition);
        } catch (Exception ex) {
            log.error("[isConditionMatched][message({}) condition({}) 条件匹配异常]", message, condition, ex);
            return false;
        }
    }

}
