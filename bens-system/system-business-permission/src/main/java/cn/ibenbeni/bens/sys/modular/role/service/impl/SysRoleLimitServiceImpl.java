package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.sys.modular.role.action.RoleBindLimitAction;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleLimit;
import cn.ibenbeni.bens.sys.modular.role.enums.RoleLimitTypeEnum;
import cn.ibenbeni.bens.sys.modular.role.factory.PermissionAssignFactory;
import cn.ibenbeni.bens.sys.modular.role.mapper.SysRoleLimitMapper;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindPermissionRequest;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindPermissionResponse;
import cn.ibenbeni.bens.sys.modular.permission.service.PermissionService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleLimitService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色权限限制服务实现类
 *
 * @author: benben
 * @time: 2025/6/9 下午10:35
 */
@Service
public class SysRoleLimitServiceImpl extends ServiceImpl<SysRoleLimitMapper, SysRoleLimit> implements SysRoleLimitService {

    @Resource
    private PermissionService permissionService;

    @Override
    public RoleBindPermissionResponse getRoleLimit(RoleBindPermissionRequest roleBindPermissionRequest) {
        // 1.整理出来一个总的相应的结构树，选择状态为空
        RoleBindPermissionResponse selectTreeStructure = permissionService.createSelectTreeStructure();

        // 2.获取角色限制所对应的菜单和功能列表
        Set<Long> roleBindLimitList = this.getRoleBindLimitList(roleBindPermissionRequest.getRoleId());

        // 3.组合结构和角色绑定的限制信息，填充选择状态，封装返回结果
        return PermissionAssignFactory.fillCheckedFlag(selectTreeStructure, roleBindLimitList);
    }

    @Override
    public void updateRoleBindLimit(RoleBindPermissionRequest roleBindPermissionRequest) {
        Map<String, RoleBindLimitAction> operateActionMap = SpringUtil.getBeansOfType(RoleBindLimitAction.class);
        for (RoleBindLimitAction roleBindLimitAction : operateActionMap.values()) {
            // 角色绑定权限限制类型：2-菜单，3-功能，-1-所有权限
            if (roleBindLimitAction.getRoleBindLimitNodeType().getCode().equals(roleBindPermissionRequest.getPermissionNodeType())) {
                roleBindLimitAction.doRoleBindLimitAction(roleBindPermissionRequest);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRoleLimit(Long roleId, Set<Long> menuIdList, Set<Long> menuOptionIdList) {
        // 删除角色所绑定的权限范围
        LambdaQueryWrapper<SysRoleLimit> removeWrapper = Wrappers.lambdaQuery(SysRoleLimit.class)
                .eq(SysRoleLimit::getRoleId, roleId);
        this.remove(removeWrapper);

        List<SysRoleLimit> sysRoleLimits = new ArrayList<>();
        // 填充菜单ID
        for (Long menuId : menuIdList) {
            SysRoleLimit sysRoleLimit = new SysRoleLimit();
            sysRoleLimit.setRoleId(roleId);
            sysRoleLimit.setBusinessId(menuId);
            sysRoleLimit.setLimitType(RoleLimitTypeEnum.MENU.getCode());
            sysRoleLimits.add(sysRoleLimit);
        }

        // 填充菜单功能ID
        for (Long menuOptionId : menuOptionIdList) {
            SysRoleLimit sysRoleLimit = new SysRoleLimit();
            sysRoleLimit.setRoleId(roleId);
            sysRoleLimit.setBusinessId(menuOptionId);
            sysRoleLimit.setLimitType(RoleLimitTypeEnum.MENU_OPTIONS.getCode());
            sysRoleLimits.add(sysRoleLimit);
        }

        if (CollUtil.isEmpty(sysRoleLimits)) {
            return;
        }

        this.saveBatch(sysRoleLimits);
    }

    @Override
    public Set<Long> getRoleBindLimitList(Long roleId) {
        return this.getRoleBindLimitList(ListUtil.toList(roleId));
    }

    @Override
    public Set<Long> getRoleBindLimitList(List<Long> roleIdList) {
        if (CollUtil.isEmpty(roleIdList)) {
            return new HashSet<>();
        }

        LambdaQueryWrapper<SysRoleLimit> queryWrapper = Wrappers.lambdaQuery(SysRoleLimit.class)
                .in(SysRoleLimit::getRoleId, roleIdList)
                .select(SysRoleLimit::getBusinessId);
        List<SysRoleLimit> list = this.list(queryWrapper);
        if (CollUtil.isEmpty(list)) {
            return new HashSet<>();
        }
        return list.stream()
                .map(SysRoleLimit::getBusinessId)
                .collect(Collectors.toSet());
    }

}
