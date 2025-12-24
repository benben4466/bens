package cn.ibenbeni.bens.iot.modular.base.controller.rule;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.iot.modular.base.entity.rule.IotSceneRuleDO;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.rule.IotSceneRulePageReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.rule.IotSceneRuleSaveReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.request.rule.IotSceneRuleUpdateStatusReq;
import cn.ibenbeni.bens.iot.modular.base.pojo.response.rule.IotSceneRuleResp;
import cn.ibenbeni.bens.iot.modular.base.service.rule.IotSceneRuleService;
import cn.ibenbeni.bens.resource.api.annotation.DeleteResource;
import cn.ibenbeni.bens.resource.api.annotation.GetResource;
import cn.ibenbeni.bens.resource.api.annotation.PostResource;
import cn.ibenbeni.bens.resource.api.annotation.PutResource;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Tag(name = "管理后台 - IoT场景联动")
@RestController
public class IotSceneRuleController {

    @Resource
    private IotSceneRuleService sceneRuleService;

    @Operation(summary = "创建场景联动")
    @PostResource(path = "/iot/scene-rule/create")
    public ResponseData<Long> createSceneRule(@RequestBody @Valid IotSceneRuleSaveReq saveReq) {
        return new SuccessResponseData<>(sceneRuleService.createSceneRule(saveReq));
    }

    @Operation(summary = "删除场景联动")
    @Parameter(name = "id", description = "场景规则ID", required = true)
    @DeleteResource(path = "/iot/scene-rule/delete")
    public ResponseData<Boolean> deleteSceneRule(@RequestParam("id") Long id) {
        sceneRuleService.deleteSceneRule(id);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新场景联动")
    @PutResource(path = "/iot/scene-rule/update")
    public ResponseData<Boolean> updateSceneRule(@RequestBody @Valid IotSceneRuleSaveReq updateReq) {
        sceneRuleService.updateSceneRule(updateReq);
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "更新场景联动状态")
    @PutResource(path = "/iot/scene-rule/update-status")
    public ResponseData<Boolean> updateSceneRuleStatus(@Valid @RequestBody IotSceneRuleUpdateStatusReq updateReqVO) {
        sceneRuleService.updateSceneRuleStatus(updateReqVO.getRuleId(), updateReqVO.getStatusFlag());
        return new SuccessResponseData<>(true);
    }

    @Operation(summary = "获得场景联动")
    @Parameter(name = "id", description = "场景规则ID", required = true, example = "10")
    @GetResource(path = "/iot/scene-rule/get")
    public ResponseData<IotSceneRuleResp> getSceneRule(@RequestParam("id") Long id) {
        IotSceneRuleDO sceneRule = sceneRuleService.getSceneRule(id);
        return new SuccessResponseData<>(BeanUtil.toBean(sceneRule, IotSceneRuleResp.class));
    }

    @Operation(summary = "获得场景联动分页")
    @GetResource(path = "/iot/scene-rule/page")
    public ResponseData<PageResult<IotSceneRuleResp>> pageSceneRule(@Valid IotSceneRulePageReq pageReq) {
        PageResult<IotSceneRuleDO> page = sceneRuleService.pageSceneRule(pageReq);
        return new SuccessResponseData<>(DbUtil.toBean(page, IotSceneRuleResp.class));
    }

}
