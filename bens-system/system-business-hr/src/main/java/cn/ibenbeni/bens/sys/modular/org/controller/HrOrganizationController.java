package cn.ibenbeni.bens.sys.modular.org.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.enums.StatusEnum;
import cn.ibenbeni.bens.rule.pojo.request.BaseRequest;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.rule.util.BeanUtils;
import cn.ibenbeni.bens.sys.modular.org.entity.HrOrganizationDO;
import cn.ibenbeni.bens.sys.modular.org.pojo.vo.DeptSimpleRespVO;
import cn.ibenbeni.bens.sys.modular.org.pojo.vo.OrgListReqVO;
import cn.ibenbeni.bens.sys.modular.org.pojo.vo.OrgPageReqVO;
import cn.ibenbeni.bens.sys.modular.org.pojo.vo.OrganizationSaveReqVO;
import cn.ibenbeni.bens.sys.modular.org.service.HrOrganizationService;
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
@RestController
public class HrOrganizationController {

    @Resource
    private HrOrganizationService organizationService;

    /**
     * 创建组织
     */
    @PostMapping("/system/org/create")
    public ResponseData<Long> createOrg(@Validated(BaseRequest.create.class) @RequestBody OrganizationSaveReqVO createReqVO) {
        return new SuccessResponseData<>(organizationService.createOrg(createReqVO));
    }

    /**
     * 删除组织
     */
    @DeleteMapping("/system/org/delete")
    public ResponseData<Boolean> deleteOrg(@RequestParam("id") Long id) {
        organizationService.deleteOrg(id);
        return new SuccessResponseData<>(true);
    }

    /**
     * 更新组织
     */
    @PutMapping("/system/org/update")
    public ResponseData<Boolean> updateOrg(@Validated(BaseRequest.update.class) @RequestBody OrganizationSaveReqVO createReqVO) {
        organizationService.updateOrg(createReqVO);
        return new SuccessResponseData<>(true);
    }

    /**
     * 获取组织信息
     */
    @GetMapping("/system/org/get")
    public ResponseData<HrOrganizationDO> getOrg(@RequestParam("id") Long id) {
        return new SuccessResponseData<>(organizationService.getOrg(id));
    }

    /**
     * 获取组织精简信息列表
     */
    @GetMapping("/system/org/simple-list")
    public ResponseData<List<DeptSimpleRespVO>> getSimpleOrgList() {
        OrgListReqVO reqVO = new OrgListReqVO();
        reqVO.setStatusFlag(StatusEnum.ENABLE.getCode());
        List<HrOrganizationDO> list = organizationService.getOrgList(reqVO);
        return new SuccessResponseData<>(BeanUtils.toBean(list, DeptSimpleRespVO.class));
    }

    /**
     * 根据条件查询组织信息列表
     */
    @GetMapping("/system/org/list")
    public ResponseData<List<HrOrganizationDO>> getOrgList(OrgListReqVO reqVO) {
        List<HrOrganizationDO> orgList = organizationService.getOrgList(reqVO);
        return new SuccessResponseData<>(orgList);
    }

    /**
     * 根据条件查询组织信息列表（分页）
     */
    @GetMapping("/system/org/page")
    public ResponseData<PageResult<HrOrganizationDO>> getOrgPage(OrgPageReqVO reqVO) {
        return new SuccessResponseData<>();
    }

    /**
     * 通用获取组织机构树
     * <p>用途：组织机构管理界面左侧树</p>
     * <p>若传递orgParentId, 则懒加载；传入-1，获取所有一级子节点</p>
     */
    @GetMapping("/system/common/org/tree")
    public ResponseData<List<HrOrganizationDO>> commonOrgTree(@RequestBody OrgListReqVO reqVO) {
        reqVO.setStatusFlag(StatusEnum.ENABLE.getCode()); // 默认查询启用的
        return new SuccessResponseData<>(organizationService.getOrgList(reqVO));
    }

}
