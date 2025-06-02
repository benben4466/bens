package cn.ibenbeni.bens.sys.modular.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.auth.api.context.LoginContext;
import cn.ibenbeni.bens.cache.api.CacheOperatorApi;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.sys.api.SysRoleServiceApi;
import cn.ibenbeni.bens.sys.api.constants.SysConstants;
import cn.ibenbeni.bens.sys.api.enums.role.RoleTypeEnum;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserRole;
import cn.ibenbeni.bens.sys.modular.user.enums.SysUserExceptionEnum;
import cn.ibenbeni.bens.sys.modular.user.mapper.SysUserRoleMapper;
import cn.ibenbeni.bens.sys.modular.user.pojo.request.SysUserRoleRequest;
import cn.ibenbeni.bens.sys.modular.user.service.SysUserRoleService;
import cn.ibenbeni.bens.sys.modular.user.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户角色关联服务实现类
 *
 * @author: benben
 * @time: 2025/6/2 下午2:20
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Resource(name = "userRoleCache")
    private CacheOperatorApi<List<SysUserRole>> userRoleCache;

    @Resource
    private SysRoleServiceApi sysRoleServiceApi;

    @Resource
    private SysUserService sysUserService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void bindRoles(SysUserRoleRequest sysUserRoleRequest) {
        // 不能修改超级管理员用户的角色，修改管理员角色可能登录会有问题
        boolean userSuperAdminFlag = sysUserService.getUserSuperAdminFlag(sysUserRoleRequest.getUserId());
        if (userSuperAdminFlag) {
            throw new ServiceException(SysUserExceptionEnum.CANT_CHANGE_ADMIN_ROLE);
        }

        // 非超级管理员不能改变系统级角色
        boolean superAdminFlag = LoginContext.me().getSuperAdminFlag();
        if (!superAdminFlag) {
            throw new ServiceException(SysUserExceptionEnum.CANT_CHANGE_BASE_SYSTEM_ROLE);
        }

        // 清空用户绑定的所有系统角色，因为这个界面只分配系统角色
        this.removeRoleAlreadyBind(sysUserRoleRequest);

        // 重新绑定用户角色信息
        List<SysUserRole> newUserRoles = this.createUserSystemRoleBinds(sysUserRoleRequest);
        this.saveBatch(newUserRoles);

        // TODO 发布修改用户绑定角色的事件
        // TODO 记录日志
    }

    @Override
    public void bindUserDefaultRole(Long userId) {
        // 给用户绑定默认角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(sysRoleServiceApi.getDefaultRoleId());
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleType(RoleTypeEnum.SYSTEM_ROLE.getCode());
        this.save(sysUserRole);

        // TODO 发布修改用户绑定角色的事件
    }

    @Override
    public SysUserRole getPointUserRole(Long userId, Long roleId, Long orgId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery(SysUserRole.class)
                .eq(SysUserRole::getUserId, userId)
                .eq(SysUserRole::getRoleId, roleId)
                .eq(SysUserRole::getRoleOrgId, orgId)
                .select(SysUserRole::getUserRoleId, SysUserRole::getUserId, SysUserRole::getRoleId, SysUserRole::getRoleType, SysUserRole::getRoleOrgId);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<Long> getUserRoleIdList(Long userId) {
        if (userId == null) {
            return new ArrayList<>();
        }

        // 先从缓存查找用户的角色
        List<SysUserRole> cachedRoleList = userRoleCache.get(userId.toString());
        if (cachedRoleList != null) {
            return cachedRoleList.stream()
                    .map(SysUserRole::getRoleId)
                    .collect(Collectors.toList());
        }

        // 数据库查找用户角色
        List<SysUserRole> sysUserRoleList = this.dbGetUserTotalRoleList(userId);
        // 查询结果缓存起来
        if (ObjectUtil.isNotEmpty(sysUserRoleList)) {
            userRoleCache.put(userId.toString(), sysUserRoleList, SysConstants.DEFAULT_SYS_CACHE_TIMEOUT_SECONDS);
        }

        return sysUserRoleList.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Long> getUserSystemRoleIdList(Long userId) {
        if (userId == null) {
            return new HashSet<>();
        }

        // 先从缓存查找用户的角色
        List<SysUserRole> cachedRoleList = userRoleCache.get(userId.toString());
        if (cachedRoleList != null) {
            return cachedRoleList.stream()
                    .filter(sysUserRole -> RoleTypeEnum.SYSTEM_ROLE.getCode().equals(sysUserRole.getRoleType()))
                    .map(SysUserRole::getRoleId)
                    .collect(Collectors.toSet());
        }

        // 数据库查找用户角色
        List<SysUserRole> sysUserRoleList = this.dbGetUserTotalRoleList(userId);
        // 查询结果缓存起来
        if (ObjectUtil.isNotEmpty(sysUserRoleList)) {
            userRoleCache.put(userId.toString(), sysUserRoleList, SysConstants.DEFAULT_SYS_CACHE_TIMEOUT_SECONDS);
        }

        return sysUserRoleList.stream()
                .filter(sysUserRole -> RoleTypeEnum.SYSTEM_ROLE.getCode().equals(sysUserRole.getRoleType()))
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Long> findUserIdsByRoleId(Long roleId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery(SysUserRole.class)
                .eq(SysUserRole::getRoleId, roleId)
                .select(SysUserRole::getUserId);
        return this.list(queryWrapper).stream()
                .map(SysUserRole::getUserId)
                .collect(Collectors.toList());
    }

    @Override
    public void validateHaveRoleBind(Set<Long> beRemovedRoleIdList) {
    }

    @Override
    public void removeRoleAction(Set<Long> beRemovedRoleIdList) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery(SysUserRole.class)
                .in(SysUserRole::getRoleId, beRemovedRoleIdList);
        this.remove(queryWrapper);
    }

    @Override
    public void validateHaveUserBind(Set<Long> beRemovedUserIdList) {
    }

    @Override
    public void removeUserAction(Set<Long> beRemovedUserIdList) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery(SysUserRole.class)
                .in(SysUserRole::getUserId, beRemovedUserIdList);
        this.remove(queryWrapper);
    }

    /**
     * 清空用户绑定的所有系统角色，这个界面只管分配系统角色
     */
    private void removeRoleAlreadyBind(SysUserRoleRequest sysUserRoleRequest) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery(SysUserRole.class)
                .eq(SysUserRole::getUserId, sysUserRoleRequest.getUserId())
                .eq(SysUserRole::getRoleType, RoleTypeEnum.SYSTEM_ROLE.getCode());
        this.remove(queryWrapper);
    }

    /**
     * 创建用户角色的绑定
     */
    private List<SysUserRole> createUserSystemRoleBinds(SysUserRoleRequest sysUserRoleRequest) {
        List<SysUserRole> newUserRoles = new ArrayList<>();
        for (Long roleId : sysUserRoleRequest.getRoleIdList()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUserRoleRequest.getUserId());
            sysUserRole.setRoleId(roleId);
            sysUserRole.setRoleType(RoleTypeEnum.SYSTEM_ROLE.getCode());
            newUserRoles.add(sysUserRole);
        }
        return newUserRoles;
    }

    /**
     * 数据库获取用户所有的角色
     */
    private List<SysUserRole> dbGetUserTotalRoleList(Long userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery(SysUserRole.class)
                .eq(SysUserRole::getUserId, userId)
                .select(SysUserRole::getRoleId, SysUserRole::getRoleOrgId, SysUserRole::getRoleType);
        return this.list(queryWrapper);
    }

}
