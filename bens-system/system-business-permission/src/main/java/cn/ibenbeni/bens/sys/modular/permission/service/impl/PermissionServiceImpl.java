package cn.ibenbeni.bens.sys.modular.permission.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.ibenbeni.bens.auth.api.SessionManagerApi;
import cn.ibenbeni.bens.auth.api.exception.AuthException;
import cn.ibenbeni.bens.auth.api.exception.enums.AuthExceptionEnum;
import cn.ibenbeni.bens.auth.api.pojo.login.LoginUser;
import cn.ibenbeni.bens.rule.util.CollectionUtils;
import cn.ibenbeni.bens.sys.api.SysUserRoleServiceApi;
import cn.ibenbeni.bens.sys.api.pojo.user.SysUserRoleDTO;
import cn.ibenbeni.bens.sys.modular.menu.entity.SysMenuDO;
import cn.ibenbeni.bens.sys.modular.menu.service.SysMenuService;
import cn.ibenbeni.bens.sys.modular.permission.service.PermissionService;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleMenuDO;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleMenuService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 权限-服务实现接口
 *
 * @author: benben
 * @time: 2025/6/8 下午5:06
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    // region 属性

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private SysUserRoleServiceApi userRoleServiceApi;

    @Resource
    private SysRoleService roleService;

    @Resource
    private SysRoleMenuService roleMenuService;

    @Resource
    private SysMenuService menuService;

    // endregion

    @Override
    public void validatePermission(String token, List<String> permissionCodeList) throws AuthException {
        // 校验用户登录Token是否空
        if (StrUtil.isBlank(token)) {
            throw new AuthException(AuthExceptionEnum.TOKEN_GET_ERROR);
        }
        // 校验需要权限编码列表是否为空
        if (CollUtil.isEmpty(permissionCodeList)) {
            throw new AuthException(AuthExceptionEnum.PERMISSION_CODE_PARAM_EMPTY);
        }

        // 从Token获取登陆用户信息
        LoginUser loginUser = sessionManagerApi.getSession(token);

        // 获取用户所拥有的权限编码列表
        Set<String> userPermissionCodeSet = getUserPermissionCodeList(loginUser.getUserId());

        boolean validateResult = userPermissionCodeSet.containsAll(permissionCodeList);
        if (!validateResult) {
            throw new AuthException(AuthExceptionEnum.PERMISSION_VALIDATE_NOT_PASS);
        }
    }

    @Override
    public Set<String> getUserPermissionCodeList(Long userId) {
        // 角色 -> 菜单 -> 权限编码
        // 1.获取用户所有拥有角色ID
        List<SysUserRoleDTO> userRoleList = userRoleServiceApi.listByUserId(userId);
        if (CollUtil.isEmpty(userRoleList)) {
            return new HashSet<>();
        }
        Set<Long> roleIdSet = CollectionUtils.convertSet(userRoleList, SysUserRoleDTO::getRoleId);

        // 2.获取角色所对应的菜单列表
        List<SysRoleMenuDO> roleMenuList = roleMenuService.listByRoleId(roleIdSet);
        Set<Long> menuIdSet = CollectionUtils.convertSet(roleMenuList, SysRoleMenuDO::getMenuId);
        List<SysMenuDO> menuList = menuService.getMenuList(menuIdSet);
        return CollectionUtils.convertSet(menuList, SysMenuDO::getPermissionCode);
    }

    // region 用户-角色相关方法

    @Override
    public void assignUserRole(Long userId, Set<Long> roleIdSet) {
        // 获取用户绑定的角色ID集合
        Set<Long> dbRoleIdSet = CollectionUtils.convertSet(userRoleServiceApi.listByUserId(userId), SysUserRoleDTO::getRoleId);
        // 计算新增和删除的角色编号
        Set<Long> roleIds = CollUtil.emptyIfNull(roleIdSet);
        Collection<Long> createRoleIds = CollUtil.subtract(roleIds, dbRoleIdSet);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbRoleIdSet, roleIds);
        // 执行新增和删除, 已授权角色忽略
        if (CollUtil.isNotEmpty(createRoleIds)) {
            userRoleServiceApi.bindUserRole(userId, CollUtil.newHashSet(createRoleIds));
        }
        if (CollUtil.isNotEmpty(deleteMenuIds)) {
            userRoleServiceApi.deleteByUserIdAndRoleIdIds(userId, CollUtil.newHashSet(deleteMenuIds));
        }
    }

    // endregion

    // region 角色-菜单相关方法

    @Override
    public void assignRoleMenu(Long roleId, Set<Long> menuIdSet) {
        // 获取角色拥有菜单编号
        Set<Long> dbMenuIdSet = CollectionUtils.convertSet(roleMenuService.listByRoleId(roleId), SysRoleMenuDO::getMenuId);
        Set<Long> menuIds = CollUtil.emptyIfNull(menuIdSet);
        // 计算新增菜单ID和删除菜单ID
        Collection<Long> createMenuIdSet = CollUtil.subtract(menuIds, dbMenuIdSet);
        Collection<Long> deleteMenuIdSet = CollUtil.subtract(dbMenuIdSet, menuIds);
        // 执行新增和删除, 对角色已授权菜单, 做忽略处理
        if (CollUtil.isNotEmpty(createMenuIdSet)) {
            createMenuIdSet.forEach(menuId -> roleMenuService.bindRoleMenus(roleId, menuId));
        }
        if (CollUtil.isNotEmpty(deleteMenuIdSet)) {
            roleMenuService.deleteByRoleIdAndMenuIds(roleId, CollUtil.newHashSet(deleteMenuIdSet));
        }
    }

    @Override
    public Set<Long> getRoleMenuListByRoleId(Long roleId) {
        return PermissionService.super.getRoleMenuListByRoleId(roleId);
    }

    @Override
    public Set<Long> getRoleMenuListByRoleId(Set<Long> roleIdSet) {
        if (CollUtil.isEmpty(roleIdSet)) {
            return Collections.emptySet();
        }

        // 若是超级管理员，则返回所有菜单
        if (roleService.hasAnySuperAdmin(roleIdSet)) {
            return CollectionUtils.convertSet(menuService.getMenuList(), SysMenuDO::getMenuId);
        }

        // 非超级管理员，则返回角色绑定的菜单
        return CollectionUtils.convertSet(roleMenuService.listByRoleId(roleIdSet), SysRoleMenuDO::getMenuId);
    }

    @Override
    public Set<Long> getMenuRoleIdListByMenuId(Long menuId) {
        return CollectionUtils.convertSet(roleMenuService.listByMenuId(menuId), SysRoleMenuDO::getRoleId);
    }

    // endregion

}
