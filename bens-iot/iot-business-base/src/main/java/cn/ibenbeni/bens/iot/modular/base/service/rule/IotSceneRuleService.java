package cn.ibenbeni.bens.iot.modular.base.service.rule;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleTriggerTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.rule.IotSceneRulePageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.rule.IotSceneRuleSaveReq;

/**
 * IOT-场景联动规则-服务
 */
public interface IotSceneRuleService {

    /**
     * 创建场景联动规则
     *
     * @return 场景联动规则ID
     */
    Long createSceneRule(IotSceneRuleSaveReq saveReq);

    /**
     * 删除场景联动规则
     *
     * @param ruleId 场景联动规则ID
     */
    void deleteSceneRule(Long ruleId);

    /**
     * 修改场景联动规则
     */
    void updateSceneRule(IotSceneRuleSaveReq updateReq);

    /**
     * 修改场景联动规则状态
     *
     * @param ruleId     场景联动规则ID
     * @param statusFlag 状态
     */
    void updateSceneRuleStatus(Long ruleId, Integer statusFlag);

    /**
     * 获取场景联动规则
     *
     * @param ruleId 场景联动规则ID
     * @return 场景联动规则
     */
    IotSceneRuleDO getSceneRule(Long ruleId);

    /**
     * 获取场景联动规则分页列表
     */
    PageResult<IotSceneRuleDO> pageSceneRule(IotSceneRulePageReq pageReq);

    /**
     * 执行场景联动规则
     * <p>使用场景: {@link IotSceneRuleTriggerTypeEnum#TIMER}</p>
     *
     * @param ruleId 场景联动规则ID
     */
    void executeSceneRuleByTimer(Long ruleId);

}
