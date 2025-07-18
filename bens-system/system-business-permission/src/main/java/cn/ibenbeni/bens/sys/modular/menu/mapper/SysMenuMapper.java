package cn.ibenbeni.bens.sys.modular.menu.mapper;

import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuDO;
import cn.ibenbeni.bens.sys.modular.menu.pojo.request.SysMenuListReq;

import java.util.List;

/**
 * 系统菜单Mapper接口
 *
 * @author: benben
 * @time: 2025/6/1 上午10:50
 */
public interface SysMenuMapper extends BaseMapperX<SysMenuDO> {

    /**
     * 根据父级菜单ID和菜单名称查询
     *
     * @param parentId 父级菜单ID
     * @param menuName 菜单名称
     * @return 菜单
     */
    default SysMenuDO selectByParentIdAndName(Long parentId, String menuName) {
        return selectOne(SysMenuDO::getMenuParentId, parentId, SysMenuDO::getMenuName, menuName);
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(SysMenuDO::getMenuParentId, parentId);
    }

    default List<SysMenuDO> selectListByPermission(String permissionCode) {
        return selectList(SysMenuDO::getPermissionCode, permissionCode);
    }

    default List<SysMenuDO> selectList(SysMenuListReq req) {
        return selectList(new LambdaQueryWrapperX<SysMenuDO>()
                .likeIfPresent(SysMenuDO::getMenuName, req.getMenuName())
                .eqIfPresent(SysMenuDO::getStatusFlag, req.getStatusFlag())
        );
    }

}
