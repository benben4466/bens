package cn.ibenbeni.bens.iot.modular.base.service.rule;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.iot.api.exception.IotException;
import cn.ibenbeni.bens.iot.api.exception.enums.IotExceptionEnum;
import cn.ibenbeni.bens.iot.modular.base.entity.device.IotDeviceDO;
import cn.ibenbeni.bens.iot.modular.base.entity.product.IotProductDO;
import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.enums.rule.IotSceneRuleTriggerTypeEnum;
import cn.ibenbeni.bens.iot.modular.base.mapper.mysql.rule.IotSceneRuleMapper;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.rule.IotSceneRulePageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.rule.IotSceneRuleSaveReq;
import cn.ibenbeni.bens.iot.modular.base.service.device.IotDeviceService;
import cn.ibenbeni.bens.iot.modular.base.service.product.IotProductService;
import cn.ibenbeni.bens.iot.modular.base.service.rule.action.IotSceneRuleAction;
import cn.ibenbeni.bens.iot.modular.base.service.rule.timer.IotSceneRuleTimerHandler;
import cn.ibenbeni.bens.module.iot.core.mq.message.IotDeviceMessage;
import cn.ibenbeni.bens.module.iot.core.util.IotDeviceMessageUtils;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.tenant.api.annotation.TenantIgnore;
import cn.ibenbeni.bens.tenant.api.util.TenantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @Resource
    private IotProductService productService;

    @Resource
    private IotDeviceService deviceService;

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

    @TenantIgnore // 忽略多租户条件；
    @Override
    public List<IotSceneRuleDO> listSceneRuleByProductIdAndDeviceIdFromCache(Long productId, Long deviceId) {
        // 获取启用的场景联动规则
        List<IotSceneRuleDO> enableRules = sceneRuleMapper.selectListByStatus(StatusEnum.ENABLE.getCode());

        return CollectionUtils.filterList(enableRules, rule -> {
            // 忽略没有触发器的场景联动规则
            if (CollUtil.isEmpty(rule.getTriggers())) {
                return false;
            }

            for (IotSceneRuleDO.Trigger trigger : rule.getTriggers()) {
                try {
                    // 校验触发器信息
                    if (trigger.getProductId() == null || trigger.getDeviceId() == null) {
                        return false;
                    }

                    // 校验是否为产品下所有设备
                    if (trigger.getProductId().equals(productId) && IotDeviceDO.DEVICE_ID_ALL.equals(trigger.getDeviceId())) {
                        return true;
                    }

                    // 校验是否为指定设备
                    return Objects.equals(trigger.getProductId(), productId) && Objects.equals(trigger.getDeviceId(), deviceId);
                } catch (Exception ex) {
                    log.warn("[getSceneRuleListByProductIdAndDeviceIdFromCache][产品({}) 设备({}) 匹配触发器异常]", productId, deviceId, ex);
                    return false;
                }
            }

            return true;
        });
    }

    @Override
    public PageResult<IotSceneRuleDO> pageSceneRule(IotSceneRulePageReq pageReq) {
        return sceneRuleMapper.pageSceneRule(pageReq);
    }

    @Override
    public void executeSceneRuleByDevice(IotDeviceMessage message) {
        // 只处理上行消息
        if (!IotDeviceMessageUtils.isUpstreamMessage(message)) {
            log.error("[onMessage][message({}) 非上行消息，不进行处理]", message);
            return;
        }

        // 获取设备信息
        IotDeviceDO device = deviceService.getDeviceFromCache(message.getDeviceId());
        // 使用设备创建者的租户执行
        TenantUtils.execute(device.getTenantId(), () -> {
            // 1.获取设备的场景联动规则
            List<IotSceneRuleDO> sceneRules = listMatchedSceneRuleByDeviceMessage(message);
            if (CollUtil.isEmpty(sceneRules)) {
                return;
            }

            // 2.执行场景联动规则
            executeSceneRuleAction(message, sceneRules);
        });
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

    /**
     * 根据设备消息-获取匹配场景联动规则
     *
     * @param deviceMessage 设备消息
     * @return 匹配场景联动规则
     */
    private List<IotSceneRuleDO> listMatchedSceneRuleByDeviceMessage(IotDeviceMessage deviceMessage) {
        // 获取设备和产品信息
        IotDeviceDO device = deviceService.getDeviceFromCache(deviceMessage.getDeviceId());
        if (device == null) {
            log.warn("[getMatchedSceneRuleListByMessage][设备({}) 不存在]", deviceMessage.getDeviceId());
            return Collections.emptyList();
        }
        IotProductDO product = productService.getProductFromCache(device.getProductId());
        if (product == null) {
            log.warn("[getMatchedSceneRuleListByMessage][产品({}) 不存在]", device.getProductId());
            return Collections.emptyList();
        }

        // 获取场景联动规则【获取到集合是跟产品/设备相关的，但是否触发器其余条件，未筛选】
        List<IotSceneRuleDO> sceneRules = listSceneRuleByProductIdAndDeviceIdFromCache(product.getProductId(), device.getDeviceId());
        if (CollUtil.isEmpty(sceneRules)) {
            return Collections.emptyList();
        }

        // 现在只筛选了和该设备相关的场景联动规则，但是触发器条件筛选
        return sceneRules;
    }

    // endregion

}
