package cn.ibenbeni.bens.sys.modular.role.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.ibenbeni.bens.auth.api.context.LoginContext;
import cn.ibenbeni.bens.db.api.factory.PageFactory;
import cn.ibenbeni.bens.db.api.factory.PageResultFactory;
import cn.ibenbeni.bens.db.api.pojo.entity.BaseEntity;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.rule.enums.permission.DataScopeTypeEnum;
import cn.ibenbeni.bens.rule.exception.base.ServiceException;
import cn.ibenbeni.bens.sys.api.callback.RemoveRoleCallbackApi;
import cn.ibenbeni.bens.sys.api.constants.SysConstants;
import cn.ibenbeni.bens.sys.api.enums.role.RoleTypeEnum;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRole;
import cn.ibenbeni.bens.sys.modular.role.enums.exception.SysRoleExceptionEnum;
import cn.ibenbeni.bens.sys.modular.role.mapper.SysRoleMapper;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.SysRoleRequest;
import cn.ibenbeni.bens.sys.modular.role.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

/**
 * 系统角色-业务实现层
 *
 * @author benben
 * @date 2025/5/3  下午10:49
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public void add(SysRoleRequest sysRoleRequest) {
        // 权限检查，针对非管理员
        this.rolePermissionValidate(sysRoleRequest);

        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(sysRoleRequest, sysRole);

        // 设置角色默认的数据范围，默认查看全部
        sysRole.setDataScopeType(DataScopeTypeEnum.DEPT_WITH_CHILD.getCode());

        this.save(sysRole);
        // TODO 添加日志
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void del(SysRoleRequest sysRoleRequest) {
        SysRole dbSysRole = this.querySysRole(sysRoleRequest);

        // 系统角色不能删除
        if (RoleTypeEnum.SYSTEM_ROLE.getCode().equals(dbSysRole.getRoleType())) {
            throw new ServiceException(SysRoleExceptionEnum.SYSTEM_ROLE_CANT_DELETE);
        }

        // 非管理员，只能删除自己公司的角色
        if (!LoginContext.me().getSuperAdminFlag()) {
            Long currentUserCompanyId = LoginContext.me().getCurrentUserCompanyId();
            if (currentUserCompanyId == null || !currentUserCompanyId.equals(dbSysRole.getRoleCompanyId())) {
                throw new ServiceException(SysRoleExceptionEnum.DEL_PERMISSION_ERROR);
            }
        }

        // 删除角色
        this.baseDelete(CollectionUtil.set(false, dbSysRole.getRoleId()));

        // 记录日志
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDelete(SysRoleRequest sysRoleRequest) {
        // 校验被删除的角色中是否有管理员角色
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery(SysRole.class)
                .in(SysRole::getRoleId, sysRoleRequest.getRoleIdList())
                .eq(SysRole::getRoleType, RoleTypeEnum.SYSTEM_ROLE.getCode());
        long haveSystemFlagCount = this.count(queryWrapper);
        if (haveSystemFlagCount > 0) {
            throw new ServiceException(SysRoleExceptionEnum.SYSTEM_ROLE_CANT_DELETE);
        }

        // 如果当前用户是非管理员，则只能删除自己公司的角色
        if (!LoginContext.me().getSuperAdminFlag()) {
            LambdaQueryWrapper<SysRole> tempWrapper = Wrappers.lambdaQuery(SysRole.class)
                    .in(SysRole::getRoleId, sysRoleRequest.getRoleIdList())
                    .ne(SysRole::getRoleCompanyId, LoginContext.me().getCurrentUserCompanyId());
            long notMeCreateCount = this.count(tempWrapper);
            if (notMeCreateCount > 0) {
                throw new ServiceException(SysRoleExceptionEnum.DEL_PERMISSION_ERROR);
            }
        }

        // 执行删除角色
        this.baseDelete(sysRoleRequest.getRoleIdList());

        // 添加日志
    }

    @Override
    public void edit(SysRoleRequest sysRoleRequest) {
        // 权限检查，针对非管理员
        this.rolePermissionValidate(sysRoleRequest);

        SysRole dbSysRole = this.querySysRole(sysRoleRequest);
        // 添加日志

        // 不允许修改角色编码
        if (!dbSysRole.getRoleCode().equals(sysRoleRequest.getRoleCode())) {
            throw new ServiceException(SysRoleExceptionEnum.SUPER_ADMIN_ROLE_CODE_ERROR);
        }

        BeanUtil.copyProperties(sysRoleRequest, dbSysRole);

        // 如果是编辑角色，改为了基础类型，则需要将公司id清空
        if (RoleTypeEnum.SYSTEM_ROLE.getCode().equals(sysRoleRequest.getRoleType())) {
            dbSysRole.setRoleCompanyId(null);
        }

        this.updateById(dbSysRole);

        // 记录日志
    }

    @Override
    public SysRole detail(SysRoleRequest sysRoleRequest) {
        return this.querySysRole(sysRoleRequest);
    }

    @Override
    public PageResult<SysRole> findPage(SysRoleRequest sysRoleRequest) {
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery(SysRole.class)
                .orderByAsc(SysRole::getRoleType)
                .orderByAsc(SysRole::getRoleSort);
        queryWrapper.select(SysRole::getRoleName, SysRole::getRoleCode, SysRole::getRoleSort, SysRole::getRoleId, BaseEntity::getCreateTime, SysRole::getRoleType, SysRole::getRoleCompanyId);

        // 非管理员用户只能查看自己创建的角色
        this.filterRolePermission(queryWrapper, sysRoleRequest);

        Page<SysRole> sysRolePage = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(sysRolePage);
    }

    @Override
    public Integer getRoleDataScopeType(Long roleId) {
        // 默认返回自己数据
        if (roleId == null) {
            return DataScopeTypeEnum.SELF.getCode();
        }

        SysRole dbRole = this.getById(roleId);
        if (dbRole == null || dbRole.getDataScopeType() == null) {
            return DataScopeTypeEnum.SELF.getCode();
        }

        return dbRole.getDataScopeType();
    }

    @Override
    public void updateRoleDataScopeType(Long roleId, Integer dataScopeType) {
        if (ObjectUtil.hasEmpty(roleId, dataScopeType)) {
            return;
        }

        LambdaUpdateWrapper<SysRole> updateWrapper = Wrappers.lambdaUpdate(SysRole.class)
                .eq(SysRole::getRoleId, roleId)
                .set(SysRole::getDataScopeType, dataScopeType);
        this.update(updateWrapper);
    }

    /**
     * 角色的类型校验，非系统管理员，只能添加公司级别的角色，并且只能添加当前登录本公司的角色
     */
    private void rolePermissionValidate(SysRoleRequest sysRoleRequest) {
        boolean superAdminFlag = LoginContext.me().getSuperAdminFlag();
        if (superAdminFlag) {
            return;
        }

        // 非管理员，只能添加公司级别的角色
        if (!RoleTypeEnum.COMPANY_ROLE.getCode().equals(sysRoleRequest.getRoleType())) {
            throw new ServiceException(SysRoleExceptionEnum.ROLE_TYPE_ERROR);
        }

        // 非管理员，只能添加本公司的角色
        if (!LoginContext.me().getCurrentUserCompanyId().equals(sysRoleRequest.getRoleCompanyId())) {
            throw new ServiceException(SysRoleExceptionEnum.ROLE_COMPANY_ERROR);
        }
    }

    private SysRole querySysRole(SysRoleRequest sysRoleRequest) {
        SysRole dbSysRole = this.getById(sysRoleRequest.getRoleId());
        if (ObjectUtil.isEmpty(dbSysRole)) {
            throw new ServiceException(SysRoleExceptionEnum.SYS_ROLE_NOT_EXISTED);
        }
        return dbSysRole;
    }

    /**
     * 删除角色的基础业务操作
     *
     * @param roleIdList 角色ID集合
     */
    private void baseDelete(Set<Long> roleIdList) {
        Map<String, RemoveRoleCallbackApi> callbackApiMap = SpringUtil.getBeansOfType(RemoveRoleCallbackApi.class);
        // 执行角色相关的校验
        for (RemoveRoleCallbackApi removeRoleCallbackApi : callbackApiMap.values()) {
            removeRoleCallbackApi.validateHaveRoleBind(roleIdList);
        }

        this.removeBatchByIds(roleIdList);

        // 执行角色相关关联业务的删除操作
        for (RemoveRoleCallbackApi removeRoleCallbackApi : callbackApiMap.values()) {
            removeRoleCallbackApi.removeRoleAction(roleIdList);
        }
    }

    /**
     * 过滤角色的权限展示
     * <p>非管理员只能看到自己的角色和自己创建的角色</p>
     * <p>用在权限界面，获取左侧角色列表</p>
     */
    private void filterRolePermission(LambdaQueryWrapper<SysRole> wrapper, SysRoleRequest sysRoleRequest) {
        // 超级管理员，直接略过
        boolean superAdminFlag = LoginContext.me().getSuperAdminFlag();
        if (superAdminFlag) {
            // 根据角色类型填充参数
            if (ObjectUtil.isNotEmpty(sysRoleRequest.getRoleType())) {
                wrapper.eq(SysRole::getRoleType, sysRoleRequest.getRoleType());
            }

            // 根据角色的所属公司ID填充参数
            if (ObjectUtil.isNotEmpty(sysRoleRequest.getRoleCompanyId())) {
                wrapper.eq(SysRole::getRoleCompanyId, sysRoleRequest.getRoleCompanyId());
            }
            return;
        }

        // 非超级管理员，直接拼好，角色类型和角色的公司id，只能查本公司的
        wrapper.eq(SysRole::getRoleType, RoleTypeEnum.COMPANY_ROLE.getCode());
        wrapper.eq(SysRole::getRoleCompanyId, LoginContext.me().getCurrentUserCompanyId());
    }

    @Override
    public Long getDefaultRoleId() {
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery(SysRole.class)
                .eq(SysRole::getRoleCode, SysConstants.DEFAULT_ROLE_CODE)
                .select(SysRole::getRoleId);
        SysRole dbRole = this.getOne(queryWrapper, false);
        return dbRole != null ? dbRole.getRoleId() : null;
    }

}
