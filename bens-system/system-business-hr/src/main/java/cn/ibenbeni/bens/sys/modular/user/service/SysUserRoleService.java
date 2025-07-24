package cn.ibenbeni.bens.sys.modular.user.service;

import cn.ibenbeni.bens.sys.api.SysUserRoleServiceApi;
import cn.ibenbeni.bens.sys.api.callback.RemoveRoleCallbackApi;
import cn.ibenbeni.bens.sys.api.callback.RemoveUserCallbackApi;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserRoleDO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 用户角色关联服务类
 *
 * @author: benben
 * @time: 2025/6/2 下午2:20
 */
public interface SysUserRoleService extends IService<SysUserRoleDO>, SysUserRoleServiceApi, RemoveUserCallbackApi, RemoveRoleCallbackApi {

    /**
     * 删除指定用户ID下的角色用户关联
     *
     * @param userId 用户ID
     */
    void deleteListByUserId(@NotNull Long userId);

    /**
     * 根据角色ID删除角色用户关联
     *
     * @param roleId 角色ID
     */
    void deleteListByRoleId(@NotNull Long roleId);

    /**
     * 根据角色ID列表查询角色用户关联
     *
     * @param roleIdSet 角色ID集合
     */
    List<SysUserRoleDO> getListByRoleIds(@NotEmpty Set<Long> roleIdSet);

}
