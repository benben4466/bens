package cn.ibenbeni.bens.sys.modular.org.controller;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.pojo.response.ResponseData;
import cn.ibenbeni.bens.rule.pojo.response.SuccessResponseData;
import cn.ibenbeni.bens.sys.modular.org.entity.HrOrganization;
import cn.ibenbeni.bens.sys.modular.org.pojo.request.HrOrganizationRequest;
import cn.ibenbeni.bens.sys.modular.org.service.HrOrganizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 组织机构信息控制器
 *
 * @author benben
 * @date 2025/5/29  下午10:30
 */
@RestController
public class HrOrganizationController {

    @Resource
    private HrOrganizationService hrOrganizationService;

    /**
     * 添加组织机构
     */
    @PostMapping("/hrOrganization/add")
    public ResponseData<HrOrganization> add(@RequestBody HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.add(hrOrganizationRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 删除组织机构
     */
    @PostMapping("/hrOrganization/delete")
    public ResponseData<?> delete(@RequestBody HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.del(hrOrganizationRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 批量删除组织机构
     */
    @PostMapping("/hrOrganization/batchDelete")
    public ResponseData<?> batchDelete(@RequestBody HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.batchDelete(hrOrganizationRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑组织机构
     */
    @PostMapping("/hrOrganization/edit")
    public ResponseData<?> edit(@RequestBody HrOrganizationRequest hrOrganizationRequest) {
        hrOrganizationService.edit(hrOrganizationRequest);
        return new SuccessResponseData<>();
    }

    /**
     * 查看组织机构详情
     */
    @GetMapping("/hrOrganization/detail")
    public ResponseData<HrOrganization> detail(HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData<>(hrOrganizationService.detail(hrOrganizationRequest));
    }

    /**
     * 获取列表（带分页）
     */
    @GetMapping("/hrOrganization/page")
    public ResponseData<PageResult<HrOrganization>> page(HrOrganizationRequest hrOrganizationRequest) {
        return new SuccessResponseData<>(hrOrganizationService.findPage(hrOrganizationRequest));
    }

}
