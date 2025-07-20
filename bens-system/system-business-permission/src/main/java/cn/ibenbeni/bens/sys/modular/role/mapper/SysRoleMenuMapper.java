package cn.ibenbeni.bens.sys.modular.role.mapper;

import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenuDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单关联Mapper接口
 *
 * @author: benben
 * @time: 2025/6/3 下午9:19
 */
public interface SysRoleMenuMapper extends BaseMapperX<SysRoleMenuDO> {

    default List<SysRoleMenuDO> selectListByRoleId(Long roleId) {
        return selectList(SysRoleMenuDO::getRoleId, roleId);
    }

    default List<SysRoleMenuDO> selectListByRoleId(Set<Long> roleIdSet) {
        return selectList(SysRoleMenuDO::getRoleId, roleIdSet);
    }

    default List<SysRoleMenuDO> selectListByMenuId(Long menuId) {
        return selectList(SysRoleMenuDO::getMenuId, menuId);
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new LambdaQueryWrapper<SysRoleMenuDO>().eq(SysRoleMenuDO::getRoleId, roleId));
    }

    default void deleteListByMenuId(Long menuId) {
        delete(new LambdaQueryWrapper<SysRoleMenuDO>().eq(SysRoleMenuDO::getMenuId, menuId));
    }

    default void deleteListByRoleId(Set<Long> roleIdSet) {
        delete(new LambdaQueryWrapper<SysRoleMenuDO>().in(SysRoleMenuDO::getRoleId, roleIdSet));
    }

    default void deleteListByMenuId(Set<Long> menuIdSet) {
        delete(new LambdaQueryWrapper<SysRoleMenuDO>().in(SysRoleMenuDO::getMenuId, menuIdSet));
    }

    default void deleteListByRoleIdAndMenuIds(Long roleId, Set<Long> menuIdSet) {
        delete(new LambdaQueryWrapper<SysRoleMenuDO>()
                .eq(SysRoleMenuDO::getRoleId, roleId)
                .in(SysRoleMenuDO::getMenuId, menuIdSet)
        );
    }

}
