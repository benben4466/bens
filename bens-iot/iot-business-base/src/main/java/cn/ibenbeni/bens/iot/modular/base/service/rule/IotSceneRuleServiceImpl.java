package cn.ibenbeni.bens.iot.modular.base.service.rule;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.api.exception.IotException;
import cn.ibenbeni.bens.iot.api.exception.enums.IotExceptionEnum;
import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.mapper.mysql.rule.IotSceneRuleMapper;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.rule.IotSceneRulePageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.rule.IotSceneRuleSaveReq;
import cn.ibenbeni.bens.iot.modular.base.service.rule.timer.IotSceneRuleTimerHandler;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * IOT-场景联动规则-服务实现类
 */
@Slf4j
@Service
public class IotSceneRuleServiceImpl implements IotSceneRuleService {

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
    }

    // endregion

    // region 私有方法

    private void validateSceneRuleExists(Long ruleId) {
        if (sceneRuleMapper.selectById(ruleId) == null) {
            throw new IotException(IotExceptionEnum.RULE_SCENE_NOT_EXISTS);
        }
    }

    // endregion

}
