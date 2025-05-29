package cn.ibenbeni.bens.sys.modular.org.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.sys.api.OrganizationServiceApi;
import cn.ibenbeni.bens.sys.modular.org.entity.HrOrganization;
import cn.ibenbeni.bens.sys.modular.org.pojo.request.HrOrganizationRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 组织机构信息服务类
 *
 * @author benben
 */
public interface HrOrganizationService extends IService<HrOrganization>, OrganizationServiceApi {

    /**
     * 新增
     */
    void add(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 删除
     */
    void del(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 批量删除
     */
    void batchDelete(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 编辑
     */
    void edit(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 查询详情
     */
    HrOrganization detail(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 获取列表
     */
    List<HrOrganization> findList(HrOrganizationRequest hrOrganizationRequest);

    /**
     * 获取列表（带分页）
     */
    PageResult<HrOrganization> findPage(HrOrganizationRequest hrOrganizationRequest);

}
