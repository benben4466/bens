package cn.ibenbeni.bens.sys.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.ibenbeni.bens.sys.api.pojo.user.SysUserRoleDTO;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserRoleDO;
import cn.ibenbeni.bens.sys.modular.user.mapper.SysUserRoleMapper;
import cn.ibenbeni.bens.sys.modular.user.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 用户角色关联服务实现类
 *
 * @author: benben
 * @time: 2025/6/2 下午2:20
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleDO> implements SysUserRoleService {

    // region 属性

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    // endregion

    // region 公共方法

    @Override
    public void bindUserRole(Long userId, Long roleId) {
        SysUserRoleDO userRole = SysUserRoleDO.builder()
                .userId(userId)
                .roleId(roleId)
                .build();
        this.save(userRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void bindUserRole(Long userId, Set<Long> roleIdSet) {
        if (CollUtil.isEmpty(roleIdSet)) {
            return;
        }
        roleIdSet.forEach(roleId -> bindUserRole(userId, roleId));
    }

    @Override
    public void deleteListByUserId(Long userId) {
        sysUserRoleMapper.deleteListByUserId(userId);
    }

    @Override
    public void deleteListByRoleId(Long roleId) {
        sysUserRoleMapper.deleteListByRoleId(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByUserIdAndRoleIdIds(Long userId, Set<Long> roleIdSet) {
        if (CollUtil.isEmpty(roleIdSet)) {
            return;
        }
        sysUserRoleMapper.deleteListByUserIdAndRoleIdIds(userId, roleIdSet);
    }

    @Override
    public List<SysUserRoleDTO> listByUserId(Long userId) {
        List<SysUserRoleDO> userRoleList = sysUserRoleMapper.selectListByUserId(userId);
        return BeanUtil.copyToList(userRoleList, SysUserRoleDTO.class);
    }

    @Override
    public List<SysUserRoleDO> getListByRoleIds(Set<Long> roleIdSet) {
        return sysUserRoleMapper.selectListByRoleIds(roleIdSet);
    }

    // endregion

    // region 删除回调

    @Override
    public void validateHaveUserBind(Set<Long> beRemovedUserIdList) {
    }

    @Override
    public void removeUserAction(Set<Long> beRemovedUserIdList) {
        sysUserRoleMapper.deleteListByUserIds(beRemovedUserIdList);
    }

    @Override
    public void validateHaveRoleBind(Set<Long> beRemovedRoleIdList) {
    }

    @Override
    public void removeRoleAction(Set<Long> beRemovedRoleIdList) {
        sysUserRoleMapper.deleteListByRoleIds(beRemovedRoleIdList);
    }

    // endregion

    // region 私有方法

    // endregion

}
