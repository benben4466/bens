package cn.ibenbeni.bens.sys.api;

import cn.ibenbeni.bens.sys.api.pojo.user.SysUserRoleDTO;

import java.util.List;
import java.util.Set;

/**
 * 用户角色关联关系的Api
 *
 * @author: benben
 * @time: 2025/6/2 下午2:22
 */
public interface SysUserRoleServiceApi {

    /**
     * 绑定用户角色
     * <p>组织架构-人员页面使用；用于绑定系统角色</p>
     */
    void bindUserRole(Long userId, Long roleId);

    /**
     * 绑定用户角色
     *
     * @param userId    用户ID
     * @param roleIdSet 角色ID集合
     */
    void bindUserRole(Long userId, Set<Long> roleIdSet);

    /**
     * 删除指定用户ID下，角色ID列表的关联
     *
     * @param userId    用户ID
     * @param roleIdSet 角色ID列表
     */
    void deleteByUserIdAndRoleIdIds(Long userId, Set<Long> roleIdSet);

    /**
     * 根据用户ID查询角色用户关联
     *
     * @param userId 用户ID
     */
    List<SysUserRoleDTO> listByUserId(Long userId);

}
