package cn.ibenbeni.bens.sys.modular.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserRoleDO;
import cn.ibenbeni.bens.sys.modular.user.mapper.SysUserRoleMapper;
import cn.ibenbeni.bens.sys.modular.user.pojo.request.UserRoleBindReq;
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
    public void bindRole(UserRoleBindReq bindReq) {
        SysUserRoleDO userRole = BeanUtil.toBean(bindReq, SysUserRoleDO.class);
        this.save(userRole);
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
    public void deleteListByUserIdAndRoleIdIds(Long userId, Set<Long> roleIdSet) {
        sysUserRoleMapper.deleteListByUserIdAndRoleIdIds(userId, roleIdSet);
    }

    @Override
    public List<SysUserRoleDO> selectListByUserId(Long userId) {
        return sysUserRoleMapper.selectListByUserId(userId);
    }

    @Override
    public List<SysUserRoleDO> selectListByRoleIds(Set<Long> roleIdSet) {
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
