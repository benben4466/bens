package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenu;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenu;
import cn.ibenbeni.bens.sys.modular.role.mapper.SysRoleMenuMapper;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色菜单关联服务实现类
 *
 * @author: benben
 * @time: 2025/6/3 下午9:20
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Resource(name = "roleMenuCache")
    private CacheOperatorApi<List<Long>> roleMenuCache;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void bindRoleMenus(Long roleId, List<SysMenu> menuList) {
        if (ObjectUtil.hasEmpty(roleId, menuList)) {
            return;
        }

        // 清空角色旧绑定关系
        LambdaQueryWrapper<SysRoleMenu> removeWrapper = Wrappers.lambdaQuery(SysRoleMenu.class)
                .eq(SysRoleMenu::getRoleId, roleId);
        this.remove(removeWrapper);

        // 绑定角色菜单
        List<SysRoleMenu> saveList = new ArrayList<>();
        for (SysMenu menu : menuList) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menu.getMenuId());
            saveList.add(sysRoleMenu);
        }
        this.saveBatch(saveList);
    }

    @Override
    public List<Long> getRoleBindMenuIdList(List<Long> roleIdList) {
        ArrayList<Long> result = new ArrayList<>();
        if (CollUtil.isEmpty(roleIdList)) {
            return result;
        }

        for (Long roleId : roleIdList) {
            List<Long> cacheMenuIdList = roleMenuCache.get(roleId.toString());
            if (CollUtil.isNotEmpty(cacheMenuIdList)) {
                result.addAll(cacheMenuIdList);
                continue;
            }

            // 数据库查询并缓存
            LambdaQueryWrapper<SysRoleMenu> queryWrapper = Wrappers.lambdaQuery(SysRoleMenu.class)
                    .in(SysRoleMenu::getRoleId, roleIdList)
                    .select(SysRoleMenu::getMenuId);
            List<SysRoleMenu> sysRoleMenuList = this.list(queryWrapper);
            if (CollUtil.isEmpty(sysRoleMenuList)) {
                continue;
            }
            List<Long> menuIdListQueryResult = sysRoleMenuList.stream()
                    .map(SysRoleMenu::getMenuId)
                    .collect(Collectors.toList());
            result.addAll(menuIdListQueryResult);

            // 缓存
            roleMenuCache.put(roleId.toString(), menuIdListQueryResult);
        }
        return result;
    }

    @Override
    public void removeMenuAction(Set<Long> beRemovedMenuIdList) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = Wrappers.lambdaQuery(SysRoleMenu.class)
                .in(SysRoleMenu::getMenuId, beRemovedMenuIdList);
        this.remove(queryWrapper);
    }

    @Override
    public void validateHaveRoleBind(Set<Long> beRemovedRoleIdList) {
    }

    @Override
    public void removeRoleAction(Set<Long> beRemovedRoleIdList) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = Wrappers.lambdaQuery(SysRoleMenu.class)
                .in(SysRoleMenu::getRoleId, beRemovedRoleIdList);
        this.remove(queryWrapper);
    }

}
