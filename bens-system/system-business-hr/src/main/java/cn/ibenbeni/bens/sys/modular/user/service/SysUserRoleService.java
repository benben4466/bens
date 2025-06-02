package cn.ibenbeni.bens.sys.modular.user.service;

import cn.ibenbeni.bens.sys.api.SysUserRoleServiceApi;
import cn.ibenbeni.bens.sys.api.callback.RemoveRoleCallbackApi;
import cn.ibenbeni.bens.sys.api.callback.RemoveUserCallbackApi;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserRole;
import cn.ibenbeni.bens.sys.modular.user.pojo.request.SysUserRoleRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户角色关联服务类
 *
 * @author: benben
 * @time: 2025/6/2 下午2:20
 */
public interface SysUserRoleService extends IService<SysUserRole>, SysUserRoleServiceApi, RemoveUserCallbackApi, RemoveRoleCallbackApi {

    /**
     * 绑定用户角色
     * <p>组织架构-人员页面使用；用于绑定系统角色</p>
     */
    void bindRoles(SysUserRoleRequest sysUserRoleRequest);

    /**
     * 给用户添加默认的角色
     */
    void bindUserDefaultRole(Long userId);

    /**
     * 获取用户是否绑定的对应的角色
     */
    SysUserRole getPointUserRole(Long userId, Long roleId, Long orgId);

}
