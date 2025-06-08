package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.ibenbeni.bens.db.api.factory.PageFactory;
import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.enums.permission.DataScopeTypeEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleDataScope;
import cn.ibenbeni.bens.sys.modular.role.enums.exception.SysRoleDataScopeExceptionEnum;
import cn.ibenbeni.bens.sys.modular.role.mapper.SysRoleDataScopeMapper;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RoleBindDataScopeRequest;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.SysRoleDataScopeRequest;
import cn.ibenbeni.bens.sys.modular.role.pojo.response.RoleBindDataScopeResponse;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleDataScopeService;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * 角色数据权限服务实现类
 *
 * @author: benben
 * @time: 2025/6/8 上午11:38
 */
@Service
public class SysRoleDataScopeServiceImpl extends ServiceImpl<SysRoleDataScopeMapper, SysRoleDataScope> implements SysRoleDataScopeService {

    @Resource
    private SysRoleService sysRoleService;

    @Override
    public void add(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        SysRoleDataScope sysRoleDataScope = BeanUtil.toBean(sysRoleDataScopeRequest, SysRoleDataScope.class);
        this.save(sysRoleDataScope);
    }

    @Override
    public void del(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        SysRoleDataScope dbRoleDataScope = this.querySysRoleDataScope(sysRoleDataScopeRequest);
        this.removeById(dbRoleDataScope.getRoleDataScopeId());
    }

    @Override
    public void edit(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        SysRoleDataScope dbRoleDataScope = this.querySysRoleDataScope(sysRoleDataScopeRequest);
        BeanUtil.copyProperties(sysRoleDataScopeRequest, dbRoleDataScope);
        this.updateById(dbRoleDataScope);
    }

    @Override
    public SysRoleDataScope detail(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        return this.querySysRoleDataScope(sysRoleDataScopeRequest);
    }

    @Override
    public List<SysRoleDataScope> findList(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        return this.list(this.createWrapper(sysRoleDataScopeRequest));
    }

    @Override
    public PageResult<SysRoleDataScope> findPage(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        Page<SysRoleDataScope> sysRolePage = this.page(PageFactory.defaultPage(), this.createWrapper(sysRoleDataScopeRequest));
        return PageResultFactory.createPageResult(sysRolePage);
    }

    @Override
    public RoleBindDataScopeResponse getRoleBindDataScope(RoleBindDataScopeRequest roleBindDataScopeRequest) {
        RoleBindDataScopeResponse roleBindDataScopeResp = new RoleBindDataScopeResponse();
        roleBindDataScopeResp.setOrgIdList(new ArrayList<>());

        // 填充数据权限类型
        Integer dataScopeType = sysRoleService.getRoleDataScopeType(roleBindDataScopeRequest.getRoleId());
        roleBindDataScopeResp.setDataScopeType(dataScopeType);

        if (!DataScopeTypeEnum.DEFINE.getCode().equals(dataScopeType)) {
            return roleBindDataScopeResp;
        }

        // 如果是指定部门，则获取指定部门的orgId集合
        Set<Long> roleBindOrgIdList = this.getRoleBindOrgIdList(ListUtil.list(false, roleBindDataScopeRequest.getRoleId()));
        roleBindDataScopeResp.setOrgIdList(ListUtil.list(false, roleBindOrgIdList));
        return roleBindDataScopeResp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRoleBindDataScope(RoleBindDataScopeRequest roleBindDataScopeRequest) {
        // TODO 记录日志

        // 移除角色旧的数据权限
        LambdaQueryWrapper<SysRoleDataScope> removeWrapper = Wrappers.lambdaQuery(SysRoleDataScope.class)
                .eq(SysRoleDataScope::getRoleId, roleBindDataScopeRequest.getRoleId());
        this.remove(removeWrapper);

        // 绑定新的角色数据权限
        sysRoleService.updateRoleDataScopeType(roleBindDataScopeRequest.getRoleId(), roleBindDataScopeRequest.getDataScopeType());

        // 非指定部门，直接返回
        if (!DataScopeTypeEnum.DEFINE.getCode().equals(roleBindDataScopeRequest.getDataScopeType())) {
            return;
        }

        // 如果是指定部门的话，则更新角色关联的指定部门的信息
        if (CollUtil.isEmpty(roleBindDataScopeRequest.getOrgIdList())) {
            return;
        }

        List<SysRoleDataScope> bindRoleDataScopeList = new ArrayList<>();
        for (Long ordId : roleBindDataScopeRequest.getOrgIdList()) {
            SysRoleDataScope roleDataScope = new SysRoleDataScope();
            roleDataScope.setRoleId(roleBindDataScopeRequest.getRoleId());
            roleDataScope.setOrganizationId(ordId);
            bindRoleDataScopeList.add(roleDataScope);
        }
        this.saveBatch(bindRoleDataScopeList);
    }

    @Override
    public Set<Long> getRoleBindOrgIdList(List<Long> roleIdList) {
        if (CollUtil.isEmpty(roleIdList)) {
            return new HashSet<>();
        }

        LambdaQueryWrapper<SysRoleDataScope> queryWrapper = Wrappers.lambdaQuery(SysRoleDataScope.class)
                .in(SysRoleDataScope::getRoleId, roleIdList)
                .select(SysRoleDataScope::getOrganizationId);
        List<SysRoleDataScope> roleDataScopes = this.list(queryWrapper);
        if (CollUtil.isEmpty(roleDataScopes)) {
            return new HashSet<>();
        }
        return roleDataScopes.stream()
                .map(SysRoleDataScope::getOrganizationId)
                .collect(Collectors.toSet());
    }

    @Override
    public void validateHaveOrgBind(Set<Long> beRemovedOrgIdList) {
    }

    @Override
    public void removeOrgAction(Set<Long> beRemovedOrgIdList) {
        if (CollUtil.isEmpty(beRemovedOrgIdList)) {
            return;
        }
        LambdaQueryWrapper<SysRoleDataScope> queryWrapper = Wrappers.lambdaQuery(SysRoleDataScope.class)
                .in(SysRoleDataScope::getOrganizationId, beRemovedOrgIdList);
        this.remove(queryWrapper);
    }

    @Override
    public void validateHaveRoleBind(Set<Long> beRemovedRoleIdList) {
    }

    @Override
    public void removeRoleAction(Set<Long> beRemovedRoleIdList) {
        if (CollUtil.isEmpty(beRemovedRoleIdList)) {
            return;
        }
        LambdaQueryWrapper<SysRoleDataScope> queryWrapper = Wrappers.lambdaQuery(SysRoleDataScope.class)
                .in(SysRoleDataScope::getRoleId, beRemovedRoleIdList);
        this.remove(queryWrapper);
    }

    private SysRoleDataScope querySysRoleDataScope(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        SysRoleDataScope dbRoleDataScope = this.getById(sysRoleDataScopeRequest.getRoleDataScopeId());
        if (ObjectUtil.isEmpty(dbRoleDataScope)) {
            throw new ServiceException(SysRoleDataScopeExceptionEnum.SYS_ROLE_DATA_SCOPE_NOT_EXISTED);
        }
        return dbRoleDataScope;
    }

    /**
     * 创建查询wrapper
     */
    private LambdaQueryWrapper<SysRoleDataScope> createWrapper(SysRoleDataScopeRequest sysRoleDataScopeRequest) {
        return Wrappers.lambdaQuery(SysRoleDataScope.class)
                .eq(ObjectUtil.isNotEmpty(sysRoleDataScopeRequest.getRoleId()), SysRoleDataScope::getRoleId, sysRoleDataScopeRequest.getRoleId());
    }

}
