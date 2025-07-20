package cn.ibenbeni.bens.sys.modular.user.mapper;

import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserRoleDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Set;

/**
 * 用户角色关联Mapper接口
 *
 * @author: benben
 * @time: 2025/6/2 下午2:19
 */
public interface SysUserRoleMapper extends BaseMapperX<SysUserRoleDO> {

    default void deleteListByUserId(Long userId) {
        delete(new LambdaQueryWrapper<SysUserRoleDO>().eq(SysUserRoleDO::getUserId, userId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new LambdaQueryWrapper<SysUserRoleDO>().eq(SysUserRoleDO::getRoleId, roleId));
    }

    default void deleteListByUserIds(Set<Long> userIdSet) {
        delete(new LambdaQueryWrapper<SysUserRoleDO>().in(SysUserRoleDO::getUserId, userIdSet));
    }

    default void deleteListByRoleIds(Set<Long> roleIdSet) {
        delete(new LambdaQueryWrapper<SysUserRoleDO>().in(SysUserRoleDO::getRoleId, roleIdSet));
    }

    /**
     * 删除指定用户ID下，角色ID列表的关联
     *
     * @param userId    用户ID
     * @param roleIdSet 角色ID列表
     */
    default void deleteListByUserIdAndRoleIdIds(Long userId, Set<Long> roleIdSet) {
        delete(new LambdaQueryWrapper<SysUserRoleDO>()
                .eq(SysUserRoleDO::getUserId, userId)
                .in(SysUserRoleDO::getRoleId, roleIdSet)
        );
    }

    default List<SysUserRoleDO> selectListByUserId(Long userId) {
        return selectList(SysUserRoleDO::getUserId, userId);
    }

    default List<SysUserRoleDO> selectListByRoleIds(Set<Long> roleIdSet) {
        return selectList(SysUserRoleDO::getRoleId, roleIdSet);
    }

}
