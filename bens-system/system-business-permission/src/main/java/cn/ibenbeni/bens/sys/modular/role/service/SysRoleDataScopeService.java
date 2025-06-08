package cn.ibenbeni.bens.sys.modular.role.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.sys.api.callback.RemoveOrgCallbackApi;
import cn.ibenbeni.bens.sys.api.callback.RemoveRoleCallbackApi;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleDataScope;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindDataScopeRequest;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.SysRoleDataScopeRequest;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindDataScopeResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 角色数据权限服务类
 *
 * @author: benben
 * @time: 2025/6/8 上午11:38
 */
public interface SysRoleDataScopeService extends IService<SysRoleDataScope>, RemoveRoleCallbackApi, RemoveOrgCallbackApi {

    /**
     * 新增
     */
    void add(SysRoleDataScopeRequest sysRoleDataScopeRequest);

    /**
     * 删除
     */
    void del(SysRoleDataScopeRequest sysRoleDataScopeRequest);

    /**
     * 编辑
     */
    void edit(SysRoleDataScopeRequest sysRoleDataScopeRequest);

    /**
     * 查询详情
     */
    SysRoleDataScope detail(SysRoleDataScopeRequest sysRoleDataScopeRequest);

    /**
     * 获取列表
     */
    List<SysRoleDataScope> findList(SysRoleDataScopeRequest sysRoleDataScopeRequest);

    /**
     * 获取列表（带分页）
     */
    PageResult<SysRoleDataScope> findPage(SysRoleDataScopeRequest sysRoleDataScopeRequest);

    /**
     * 获取角色绑定的数据权限
     */
    RoleBindDataScopeResponse getRoleBindDataScope(RoleBindDataScopeRequest roleBindDataScopeRequest);

    /**
     * 角色绑定数据范围
     */
    void updateRoleBindDataScope(RoleBindDataScopeRequest roleBindDataScopeRequest);

    /**
     * 获取角色绑定的机构ID列表
     *
     * @param roleIdList 角色ID列表
     */
    Set<Long> getRoleBindOrgIdList(List<Long> roleIdList);

}
