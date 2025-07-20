package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenuDO;
import cn.ibenbeni.bens.sys.modular.role.mapper.SysRoleMenuMapper;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单关联服务实现类
 *
 * @author: benben
 * @time: 2025/6/3 下午9:20
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuDO> implements SysRoleMenuService {

    // region 属性

    private SysRoleMenuMapper sysRoleMenuMapper;

    // endregion

    // region 公共方法

    @Override
    public void bindRoleMenus(Long roleId, Long menuId) {
        SysRoleMenuDO roleMenu = SysRoleMenuDO.builder()
                .roleId(roleId)
                .menuId(menuId)
                .build();
        save(roleMenu);
    }

    @Override
    public List<SysRoleMenuDO> getListByRoleId(Long roleId) {
        return sysRoleMenuMapper.selectListByRoleId(roleId);
    }

    @Override
    public List<SysRoleMenuDO> getListByRoleId(Set<Long> roleIdSet) {
        return sysRoleMenuMapper.selectListByRoleId(roleIdSet);
    }

    @Override
    public List<SysRoleMenuDO> getListByMenuId(Long menuId) {
        return sysRoleMenuMapper.selectListByMenuId(menuId);
    }

    @Override
    public void deleteListByRoleId(Long roleId) {
        sysRoleMenuMapper.deleteListByRoleId(roleId);
    }

    @Override
    public void deleteListByMenuId(Long menuId) {
        sysRoleMenuMapper.deleteListByMenuId(menuId);
    }

    @Override
    public void deleteListByRoleIdAndMenuIds(Long roleId, Set<Long> menuIdSet) {
        sysRoleMenuMapper.deleteListByRoleIdAndMenuIds(roleId, menuIdSet);
    }

    // endregion

    // region 删除回调方法

    @Override
    public void removeMenuAction(Set<Long> beRemovedMenuIdList) {
        sysRoleMenuMapper.deleteListByMenuId(beRemovedMenuIdList);
    }

    @Override
    public void validateHaveRoleBind(Set<Long> beRemovedRoleIdList) {
    }

    @Override
    public void removeRoleAction(Set<Long> beRemovedRoleIdList) {
        sysRoleMenuMapper.deleteListByRoleId(beRemovedRoleIdList);
    }

    // endregion

    // region 私有方法

    // endregion

}
