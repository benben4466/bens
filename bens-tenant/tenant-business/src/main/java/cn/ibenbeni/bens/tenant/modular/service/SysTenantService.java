package cn.ibenbeni.bens.tenant.modular.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.tenant.modular.entity.SysTenant;
import cn.ibenbeni.bens.tenant.modular.pojo.request.SysTenantRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 租户服务类
 *
 * @author: benben
 * @time: 2025/7/1 上午11:05
 */
public interface SysTenantService extends IService<SysTenant> {

    /**
     * 新增
     */
    void add(SysTenantRequest sysTenantRequest);

    /**
     * 删除
     */
    void del(SysTenantRequest sysTenantRequest);

    /**
     * 批量删除
     */
    void batchDelete(SysTenantRequest sysTenantRequest);

    /**
     * 编辑
     */
    void edit(SysTenantRequest sysTenantRequest);

    /**
     * 查询详情
     */
    SysTenant detail(SysTenantRequest sysTenantRequest);

    /**
     * 根据租户名称查询
     */
    SysTenant getByTenantName(String tenantName);

    /**
     * 查询列表
     */
    List<SysTenant> findList(SysTenantRequest sysTenantRequest);

    /**
     * 查询列表（分页）
     */
    PageResult<SysTenant> findPage(SysTenantRequest sysTenantRequest);

}
