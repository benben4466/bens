package cn.ibenbeni.bens.sys.modular.role.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.sys.api.SysRoleServiceApi;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRole;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.SysRoleRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统角色-服务类
 *
 * @author benben
 */
public interface SysRoleService extends IService<SysRole>, SysRoleServiceApi {

    /**
     * 新增
     */
    void add(SysRoleRequest sysRoleRequest);

    /**
     * 删除
     */
    void del(SysRoleRequest sysRoleRequest);

    /**
     * 批量删除
     */
    void batchDelete(SysRoleRequest sysRoleRequest);

    /**
     * 编辑
     */
    void edit(SysRoleRequest sysRoleRequest);

    /**
     * 查询详情
     */
    SysRole detail(SysRoleRequest sysRoleRequest);

    /**
     * 获取列表（带分页）
     */
    PageResult<SysRole> findPage(SysRoleRequest sysRoleRequest);

    /**
     * 获取角色的数据范围类型
     */
    Integer getRoleDataScopeType(Long roleId);

    /**
     * 更新角色的数据范围类型
     *
     * @param roleId        角色ID
     * @param dataScopeType 数据范围类型
     */
    void updateRoleDataScopeType(Long roleId, Integer dataScopeType);

    /**
     * 获取所有角色列表
     * <p>用在权限分配界面，左侧的角色列表</p>
     */
    List<SysRole> permissionGetRoleList(SysRoleRequest sysRoleRequest);

    /**
     * 用户分配角色界面，获取角色列表
     */
    List<SysRole> userAssignRoleList(SysRoleRequest sysRoleRequest);

}
