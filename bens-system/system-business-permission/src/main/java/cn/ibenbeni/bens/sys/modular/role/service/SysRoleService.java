package cn.ibenbeni.bens.sys.modular.role.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleDO;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RolePageReq;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleSaveReq;
import cn.ibenbeni.bens.tenant.api.callback.RemoveTenantCallbackApi;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * 系统角色-服务类
 *
 * @author benben
 */
public interface SysRoleService extends IService<SysRoleDO>, RemoveTenantCallbackApi {

    /**
     * 创建角色
     *
     * @param req      创建角色信息
     * @param roleType 角色类型
     * @return 角色ID
     */
    Long createRole(@Valid RoleSaveReq req, Integer roleType);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     */
    void deleteRole(Long roleId);

    /**
     * 批量删除角色
     *
     * @param roleIdSet 角色ID集合
     */
    void deleteRole(Set<Long> roleIdSet);

    /**
     * 更新角色
     */
    void updateRole(@Valid RoleSaveReq req);

    /**
     * 设置角色的数据权限
     *
     * @param roleId           角色ID
     * @param dataScopeType    数据权限类型
     * @param dataScopeDeptIds 数据权限部门ID集合
     */
    void updateRoleDataScope(Long roleId, Integer dataScopeType, Set<Long> dataScopeDeptIds);

    /**
     * 获取角色信息
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    SysRoleDO getRole(Long roleId);

    /**
     * 获得角色列表
     *
     * @param roleIdSet 角色ID集合
     * @return 角色列表
     */
    List<SysRoleDO> getRoleList(Set<Long> roleIdSet);

    /**
     * 获得角色列表
     *
     * @param statusSet 筛选的状态
     * @return 角色列表
     */
    List<SysRoleDO> getRoleListByStatus(Set<Integer> statusSet);

    /**
     * 获得所有角色列表
     *
     * @return 角色列表
     */
    List<SysRoleDO> getRoleList();

    /**
     * 获得角色分页
     *
     * @return 角色分页结果
     */
    PageResult<SysRoleDO> getRolePage(RolePageReq pageReq);

    /**
     * 判断角色ID集合中是否有管理员角色
     */
    boolean hasAnySuperAdmin(Set<Long> roleIdSet);

    /**
     * 校验角色们是否有效。如下情况，视为无效：
     * <p>1.角色编号不存在；2.角色被禁用</p>
     */
    void validateRole(Set<Long> roleIdSet);

}
