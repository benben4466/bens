package cn.ibenbeni.bens.iot.modular.base.job.rule;

import cn.hutool.core.map.MapUtil;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleTriggerTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.service.rule.IotSceneRuleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.Map;

/**
 * IOT-场景联动规则-定时任务
 * <p>执行 {@link IotSceneRuleTriggerTypeEnum#TIMER} 类型场景规则任务</p>
 */
@Slf4j
public class IotSceneRuleJob extends QuartzJobBean {

    /**
     * JobData Key - 规则场景编号
     */
    public static final String JOB_DATA_KEY_RULE_SCENE_ID = "sceneRuleId";

    @Resource
    private IotSceneRuleService sceneRuleService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 获取场景规则ID
        long sceneRuleId = context.getMergedJobDataMap().getLong(JOB_DATA_KEY_RULE_SCENE_ID);

        // 执行场景联动规则
        sceneRuleService.executeSceneRuleByTimer(sceneRuleId);
    }

    /**
     * 创建 JobData Map
     *
     * @param sceneRuleId 规则场景编号
     * @return JobData Map
     */
    public static Map<String, Object> buildJobDataMap(Long sceneRuleId) {
        return MapUtil.of(JOB_DATA_KEY_RULE_SCENE_ID, sceneRuleId);
    }

    /**
     * 创建 Job 名字
     *
     * @param sceneRuleId 规则场景编号
     * @return Job 名字
     */
    public static String buildJobName(Long sceneRuleId) {
        return String.format("%s_%d", IotSceneRuleJob.class.getSimpleName(), sceneRuleId);
    }

}
