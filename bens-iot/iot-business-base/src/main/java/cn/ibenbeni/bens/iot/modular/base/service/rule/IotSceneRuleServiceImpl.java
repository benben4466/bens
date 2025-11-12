package cn.ibenbeni.bens.iot.modular.base.service.rule;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.api.exception.IotException;
import cn.ibenbeni.bens.iot.api.exception.enums.IotExceptionEnum;
import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleTriggerTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.mapper.mysql.rule.IotSceneRuleMapper;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.rule.IotSceneRulePageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.rule.IotSceneRuleSaveReq;
import cn.ibenbeni.bens.iot.modular.base.service.rule.action.IotSceneRuleAction;
import cn.ibenbeni.bens.iot.modular.base.service.rule.timer.IotSceneRuleTimerHandler;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.tenant.api.util.TenantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * IOT-场景联动规则-服务实现类
 */
@Slf4j
@Service
public class IotSceneRuleServiceImpl implements IotSceneRuleService {

    /**
     * 场景联动规则-执行器
     */
    @Resource
    private List<IotSceneRuleAction> sceneRuleActions;

    @Resource
    private IotSceneRuleMapper sceneRuleMapper;

    @Resource
    private IotSceneRuleTimerHandler timerHandler;

    // region 公共方法

    @Override
    public Long createSceneRule(IotSceneRuleSaveReq saveReq) {

        IotSceneRuleDO sceneRule = BeanUtil.toBean(saveReq, IotSceneRuleDO.class);
        sceneRuleMapper.insert(sceneRule);

        // 注册定时触发器
        timerHandler.registerTimerTriggers(sceneRule);

        return sceneRule.getId();
    }

    @Override
    public void deleteSceneRule(Long ruleId) {
        // 校验场景联动规则是否存在
        validateSceneRuleExists(ruleId);

        sceneRuleMapper.deleteById(ruleId);

        // 删除定时触发器
        timerHandler.unregisterTimerTriggers(ruleId);
    }

    @Override
    public void updateSceneRule(IotSceneRuleSaveReq updateReq) {
        // 校验场景联动规则是否存在
        validateSceneRuleExists(updateReq.getId());

        IotSceneRuleDO updateDO = BeanUtil.toBean(updateReq, IotSceneRuleDO.class);
        sceneRuleMapper.updateById(updateDO);

        // 更新定时触发器
        timerHandler.updateTimerTriggers(updateDO);
    }

    @Override
    public void updateSceneRuleStatus(Long ruleId, Integer statusFlag) {
        // 校验场景联动规则是否存在
        validateSceneRuleExists(ruleId);

        // 更新场景联动规则状态
        IotSceneRuleDO updateDO = IotSceneRuleDO.builder()
                .id(ruleId)
                .statusFlag(statusFlag)
                .build();
        sceneRuleMapper.updateById(updateDO);

        // 根据规则状态，对定时任务进行处理
        if (StatusEnum.isEnable(statusFlag)) {
            // 启用时，获取完整的场景规则信息并注册定时触发器
            IotSceneRuleDO sceneRule = sceneRuleMapper.selectById(ruleId);
            if (sceneRule != null) {
                timerHandler.registerTimerTriggers(sceneRule);
            }
        } else {
            // 禁用时，暂停定时触发器
            timerHandler.pauseTimerTriggers(ruleId);
        }
    }

    @Override
    public IotSceneRuleDO getSceneRule(Long ruleId) {
        return sceneRuleMapper.selectById(ruleId);
    }

    @Override
    public PageResult<IotSceneRuleDO> pageSceneRule(IotSceneRulePageReq pageReq) {
        return sceneRuleMapper.pageSceneRule(pageReq);
    }

    @Override
    public void executeSceneRuleByTimer(Long ruleId) {
        // 获取场景联动规则
        IotSceneRuleDO sceneRule = TenantUtils.executeIgnore(() -> sceneRuleMapper.selectById(ruleId));
        if (sceneRule == null) {
            log.error("[executeSceneRuleByTimer][规则场景({}) 不存在]", ruleId);
            return;
        }
        if (StatusEnum.isDisable(sceneRule.getStatusFlag())) {
            log.info("[executeSceneRuleByTimer][规则场景({}) 已被禁用]", ruleId);
            return;
        }

        // 执行前判断触发器类是否为 定时触发器[确保执行的是定时触发器]
        IotSceneRuleDO.Trigger config = CollUtil.findOne(
                sceneRule.getTriggers(),
                trigger -> IotSceneRuleTriggerTypeEnum.TIMER.getType().equals(trigger.getType())
        );
        if (config == null) {
            log.error("[executeSceneRuleByTimer][规则场景({}) 不存在定时触发器]", sceneRule);
            return;
        }

        // 执行场景联动规则
        TenantUtils.execute(
                sceneRule.getTenantId(), // 指定场景规则租户执行
                () -> executeSceneRuleAction(null, ListUtil.toList(sceneRule))
        );
    }

    // endregion

    // region 私有方法

    private void validateSceneRuleExists(Long ruleId) {
        if (sceneRuleMapper.selectById(ruleId) == null) {
            throw new IotException(IotExceptionEnum.RULE_SCENE_NOT_EXISTS);
        }
    }

    /**
     * 执行场景联动规则
     *
     * @param message    设备消息
     * @param sceneRules 场景联动规则
     */
    private void executeSceneRuleAction(IotDeviceMessage message, List<IotSceneRuleDO> sceneRules) {
        sceneRules.forEach(sceneRule -> {
            sceneRule.getActions().forEach(actionConfig -> {
                // 根据执行器配置的类型，获取执行器
                List<IotSceneRuleAction> actions = CollectionUtils.filterList(
                        sceneRuleActions,
                        action -> action.getType().getType().equals(actionConfig.getType())
                );
                if (CollUtil.isEmpty(actions)) {
                    return;
                }

                // 执行动作【串行】
                actions.forEach(action -> {
                    try {
                        action.execute(message, sceneRule, actionConfig);
                        log.info("[executeSceneRuleAction][消息({}) 规则场景编号({}) 的执行动作({}) 成功]", message, sceneRule.getId(), actionConfig);
                    } catch (Exception ex) {
                        log.error("[executeSceneRuleAction][消息({}) 规则场景编号({}) 的执行动作({}) 执行异常]", message, sceneRule.getId(), actionConfig, ex);
                    }
                });
            });
        });
    }

    // endregion

}
