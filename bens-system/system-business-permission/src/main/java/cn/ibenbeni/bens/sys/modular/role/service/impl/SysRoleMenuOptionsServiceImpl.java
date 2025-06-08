package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuOptions;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenuOptions;
import cn.ibenbeni.bens.sys.modular.role.mapper.SysRoleMenuOptionsMapper;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuOptionsService;
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
 * 角色和菜单下的功能关联服务实现类
 *
 * @author: benben
 * @time: 2025/6/8 下午4:31
 */
@Service
public class SysRoleMenuOptionsServiceImpl extends ServiceImpl<SysRoleMenuOptionsMapper, SysRoleMenuOptions> implements SysRoleMenuOptionsService {

    @Resource(name = "roleMenuOptionsCache")
    private CacheOperatorApi<List<Long>> roleMenuOptionsCache;

    @Override
    public void removeRoleBindOptions(Long optionsId) {
        if (optionsId == null) {
            return;
        }

        LambdaQueryWrapper<SysRoleMenuOptions> removeWrapper = Wrappers.lambdaQuery(SysRoleMenuOptions.class)
                .eq(SysRoleMenuOptions::getMenuOptionId, optionsId);
        this.remove(removeWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void bindRoleMenuOptions(Long roleId, List<SysMenuOptions> sysMenuOptionsList) {
        if (ObjectUtil.hasEmpty(roleId, sysMenuOptionsList)) {
            return;
        }

        // 清空角色绑定的旧数据
        LambdaQueryWrapper<SysRoleMenuOptions> removeWrapper = Wrappers.lambdaQuery(SysRoleMenuOptions.class)
                .eq(SysRoleMenuOptions::getRoleId, roleId);
        this.remove(removeWrapper);

        // 绑定角色的菜单功能
        List<SysRoleMenuOptions> roleMenuOptionList = new ArrayList<>();
        for (SysMenuOptions menuOption : sysMenuOptionsList) {
            SysRoleMenuOptions roleMenuOptions = new SysRoleMenuOptions();
            roleMenuOptions.setRoleId(roleId);
            roleMenuOptions.setMenuOptionId(menuOption.getMenuOptionId());
            roleMenuOptions.setMenuId(menuOption.getMenuId());
            roleMenuOptionList.add(roleMenuOptions);
        }

        this.saveBatch(roleMenuOptionList);

        // 清空角色和菜单功能的绑定缓存
        roleMenuOptionsCache.remove(String.valueOf(roleId));
    }

    @Override
    public List<Long> getRoleBindMenuOptionsIdList(List<Long> roleIdList, boolean prioritizeCaching) {
        List<Long> result = new ArrayList<>();
        if (CollUtil.isEmpty(roleIdList)) {
            return result;
        }

        for (Long roleId : roleIdList) {
            String roleIdStr = String.valueOf(roleId);

            if (prioritizeCaching) {
                // 从缓存获取
                List<Long> optionsCached = roleMenuOptionsCache.get(roleIdStr);
                if (CollUtil.isNotEmpty(optionsCached)) {
                    result.addAll(optionsCached);
                    continue;
                }
            }

            // 缓存没有，则从数据库获取
            LambdaQueryWrapper<SysRoleMenuOptions> queryWrapper = Wrappers.lambdaQuery(SysRoleMenuOptions.class)
                    .eq(SysRoleMenuOptions::getRoleId, roleId);
            List<SysRoleMenuOptions> roleMenuOptions = this.list(queryWrapper);
            if (CollUtil.isEmpty(roleMenuOptions)) {
                continue;
            }
            List<Long> menuOptionsIdQueryResult = roleMenuOptions.stream()
                    .map(SysRoleMenuOptions::getMenuOptionId)
                    .collect(Collectors.toList());
            result.addAll(menuOptionsIdQueryResult);

            // 加入缓存
            roleMenuOptionsCache.put(roleIdStr, menuOptionsIdQueryResult);
        }

        return result;
    }

    @Override
    public void removeMenuAction(Set<Long> beRemovedMenuIdList) {
        if (CollUtil.isEmpty(beRemovedMenuIdList)) {
            return;
        }
        LambdaQueryWrapper<SysRoleMenuOptions> removeWrapper = Wrappers.lambdaQuery(SysRoleMenuOptions.class)
                .in(SysRoleMenuOptions::getMenuId, beRemovedMenuIdList);
        this.remove(removeWrapper);
    }

    @Override
    public void validateHaveRoleBind(Set<Long> beRemovedRoleIdList) {
    }

    @Override
    public void removeRoleAction(Set<Long> beRemovedRoleIdList) {
        if (CollUtil.isEmpty(beRemovedRoleIdList)) {
            return;
        }
        LambdaQueryWrapper<SysRoleMenuOptions> removeWrapper = Wrappers.lambdaQuery(SysRoleMenuOptions.class)
                .in(SysRoleMenuOptions::getRoleId, beRemovedRoleIdList);
        this.remove(removeWrapper);
    }

}
