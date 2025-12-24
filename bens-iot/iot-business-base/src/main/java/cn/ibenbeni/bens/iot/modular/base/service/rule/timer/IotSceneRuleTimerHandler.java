package cn.ibenbeni.bens.iot.modular.base.service.rule.timer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleTriggerTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.framework.core.schedule.IotSchedulerManager;
import cn.ibenbeni.bens.iot.modular.base.job.rule.IotSceneRuleJob;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.rule.util.ScheduleUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * IOT-场景联动规则-定时任务处理类
 */
@Slf4j
@Component
public class IotSceneRuleTimerHandler {

    @Resource(name = "iotSchedulerManager")
    private IotSchedulerManager schedulerManager;

    /**
     * 注册场景规则的定时触发器
     *
     * @param sceneRule 场景联动规则
     */
    public void registerTimerTriggers(IotSceneRuleDO sceneRule) {
        if (sceneRule == null || CollUtil.isEmpty(sceneRule.getTriggers())) {
            return;
        }

        // 过滤掉非定时触发器
        List<IotSceneRuleDO.Trigger> timerTriggers = CollectionUtils.filterList(
                sceneRule.getTriggers(),
                trigger -> IotSceneRuleTriggerTypeEnum.TIMER.getType().equals(trigger.getType())
        );
        if (CollUtil.isEmpty(timerTriggers)) {
            return;
        }

        // 遍历并注册定时触发器
        timerTriggers.forEach(trigger -> registerSingleTimerTrigger(sceneRule, trigger));
    }

    /**
     * 注册单个场景规则的定时触发器
     *
     * @param sceneRule 场景联动规则
     * @param trigger   定时触发器
     */
    private void registerSingleTimerTrigger(IotSceneRuleDO sceneRule, IotSceneRuleDO.Trigger trigger) {
        // 校验 CRON 表达式
        if (StrUtil.isBlank(trigger.getCronExpression())) {
            log.error("[registerSingleTimerTrigger][场景规则({}) 定时触发器缺少 CRON 表达式]", sceneRule.getId());
            return;
        }
        if (!ScheduleUtils.isValidCronExpression(trigger.getCronExpression())) {
            log.error("[registerSingleTimerTrigger][场景规则({}) 定时触发器 CRON 表达式({})非法]", sceneRule.getId(), trigger.getCronExpression());
            return;
        }

        try {
            // 构建 Job 名称
            String jobName = IotSceneRuleJob.buildJobName(sceneRule.getId());
            // 添加定时任务
            schedulerManager.addOrUpdateJob(
                    IotSceneRuleJob.class,
                    jobName,
                    trigger.getCronExpression(),
                    IotSceneRuleJob.buildJobDataMap(sceneRule.getId())
            );
            log.info("[registerSingleTimerTrigger][场景规则({}) 定时触发器注册成功，CRON: {}]", sceneRule.getId(), trigger.getCronExpression());
        } catch (SchedulerException ex) {
            log.error("[registerSingleTimerTrigger][场景规则({}) 定时触发器注册失败，CRON: {}]", sceneRule.getId(), trigger.getCronExpression(), ex);
        }
    }

    /**
     * 更新场景规则的定时触发器
     *
     * @param sceneRule 场景联动规则
     */
    public void updateTimerTriggers(IotSceneRuleDO sceneRule) {
        if (sceneRule == null) {
            return;
        }

        // 删除旧定时任务
        unregisterTimerTriggers(sceneRule.getId());

        // 若禁用，则不注册定时任务
        if (StatusEnum.isDisable(sceneRule.getStatusFlag())) {
            log.info("[updateTimerTriggers][场景规则({}) 已禁用，不注册定时触发器]", sceneRule.getId());
            return;
        }

        // 重新注册定时任务
        registerTimerTriggers(sceneRule);
    }

    /**
     * 注销场景规则的定时触发器
     *
     * @param sceneRuleId 场景联动规则ID
     */
    public void unregisterTimerTriggers(Long sceneRuleId) {
        if (sceneRuleId == null) {
            return;
        }

        // 构建 Job 名称
        String jobName = IotSceneRuleJob.buildJobName(sceneRuleId);
        try {
            schedulerManager.deleteJob(jobName);
            log.info("[unregisterTimerTriggers][场景规则({}) 定时触发器注销成功]", sceneRuleId);
        } catch (Exception ex) {
            log.error("[unregisterTimerTriggers][场景规则({}) 定时触发器注销失败]", sceneRuleId, ex);
        }
    }

    /**
     * 暂停场景规则的定时触发器
     *
     * @param sceneRuleId 场景联动规则ID
     */
    public void pauseTimerTriggers(Long sceneRuleId) {
        if (sceneRuleId == null) {
            return;
        }

        // 构建 Job 名称
        String jobName = IotSceneRuleJob.buildJobName(sceneRuleId);
        try {
            schedulerManager.pauseJob(jobName);
            log.info("[pauseTimerTriggers][场景规则({}) 定时触发器暂停成功]", sceneRuleId);
        } catch (SchedulerException e) {
            log.error("[pauseTimerTriggers][场景规则({}) 定时触发器暂停失败]", sceneRuleId, e);
        }
    }

}
