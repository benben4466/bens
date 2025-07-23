package cn.ibenbeni.bens.sys.modular.org.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.util.DbUtil;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import cn.ibenbeni.bens.sys.modular.org.entity.HrOrganizationDO;
import cn.ibenbeni.bens.sys.modular.org.pojo.request.OrgListReq;
import cn.ibenbeni.bens.sys.modular.org.pojo.request.OrgPageReq;
import cn.ibenbeni.bens.sys.modular.org.pojo.request.OrganizationSaveReq;
import cn.ibenbeni.bens.sys.modular.org.pojo.response.DeptSimpleResp;
import cn.ibenbeni.bens.sys.modular.org.pojo.response.HrOrganizationResp;
import cn.ibenbeni.bens.sys.modular.org.service.HrOrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织机构信息控制器
 *
 * @author benben
 * @date 2025/5/29  下午10:30
 */
@Tag(name = "管理后台 - 组织")
@RestController
public class HrOrganizationController {

    @Resource
    private HrOrganizationService organizationService;

    /**
     * 创建组织
     */
    @Operation(summary = "创建组织")
    @PostMapping("/system/org/create")
    public ResponseData<Long> createOrg(@Validated @RequestBody OrganizationSaveReq createReqVO) {
        return new SuccessResponseData<>(organizationService.createOrg(createReqVO));
    }

    /**
     * 删除组织
     */
    @Operation(summary = "删除组织")
    @Parameter(name = "id", description = "组织ID", required = true, example = "10")
    @DeleteMapping("/system/org/delete")
    public ResponseData<Boolean> deleteOrg(@RequestParam("id") Long id) {
        organizationService.deleteOrg(id);
        return new SuccessResponseData<>(true);
    }

    /**
     * 更新组织
     */
    @Operation(summary = "更新组织")
    @PutMapping("/system/org/update")
    public ResponseData<Boolean> updateOrg(@Validated @RequestBody OrganizationSaveReq createReqVO) {
        organizationService.updateOrg(createReqVO);
        return new SuccessResponseData<>(true);
    }

    /**
     * 获取组织信息
     */
    @Operation(summary = "获取组织信息")
    @Parameter(name = "id", description = "组织ID", required = true, example = "10")
    @GetMapping("/system/org/get")
    public ResponseData<HrOrganizationResp> getOrg(@RequestParam("id") Long id) {
        HrOrganizationDO org = organizationService.getOrg(id);
        return new SuccessResponseData<>(BeanUtils.toBean(org, HrOrganizationResp.class));
    }

    /**
     * 获取组织精简信息列表
     */
    @Operation(summary = "获取组织精简信息列表", description = "仅包含状态为启用的组织, 主要用于下拉选项")
    @GetMapping("/system/org/simple-list")
    public ResponseData<List<DeptSimpleResp>> getSimpleOrgList() {
        OrgListReq reqVO = new OrgListReq();
        reqVO.setStatusFlag(StatusEnum.ENABLE.getCode());
        List<HrOrganizationDO> list = organizationService.getOrgList(reqVO);
        return new SuccessResponseData<>(BeanUtils.toBean(list, DeptSimpleResp.class));
    }

    /**
     * 根据条件查询组织信息列表
     */
    @Operation(summary = "查询组织信息列表", description = "根据指定条件查询")
    @GetMapping("/system/org/list")
    public ResponseData<List<HrOrganizationResp>> getOrgList(OrgListReq reqVO) {
        List<HrOrganizationDO> orgList = organizationService.getOrgList(reqVO);
        return new SuccessResponseData<>(BeanUtils.toBean(orgList, HrOrganizationResp.class));
    }

    /**
     * 根据条件查询组织信息列表（分页）
     */
    @Operation(summary = "查询组织信息列表(分页)", description = "根据指定条件分页")
    @GetMapping("/system/org/page")
    public ResponseData<PageResult<HrOrganizationResp>> getOrgPage(OrgPageReq reqVO) {
        PageResult<HrOrganizationDO> pageResult = organizationService.getOrgPage(reqVO);
        return new SuccessResponseData<>(DbUtil.toBean(pageResult, HrOrganizationResp.class));
    }

    /**
     * 通用获取组织机构树
     * <p>用途：组织机构管理界面左侧树</p>
     * <p>若传递orgParentId, 则懒加载；传入-1，获取所有一级子节点</p>
     */
    @Operation(summary = "通用获取组织机构树", description = "仅获取直接子节点")
    @GetMapping("/system/common/org/tree")
    public ResponseData<List<HrOrganizationResp>> commonOrgTree(@RequestBody OrgListReq reqVO) {
        reqVO.setStatusFlag(StatusEnum.ENABLE.getCode()); // 默认查询启用的
        List<HrOrganizationDO> orgList = organizationService.getOrgList(reqVO);
        return new SuccessResponseData<>(BeanUtils.toBean(orgList, HrOrganizationResp.class));
    }

}
