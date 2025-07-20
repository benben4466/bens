package cn.ibenbeni.bens.sys.modular.role.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.sys.modular.role.entity.SysRoleDO;
import cn.ibenbeni.bens.sys.modular.role.pojo.request.RolePageReq;

import java.util.List;
import java.util.Set;

/**
 * 系统角色-Mapper接口
 *
 * @author benben
 * @date 2025/5/3  下午10:47
 */
public interface SysRoleMapper extends BaseMapperX<SysRoleDO> {

    default SysRoleDO selectByName(String roleName) {
        return selectOne(SysRoleDO::getRoleName, roleName);
    }

    default SysRoleDO selectByCode(String roleCode) {
        return selectOne(SysRoleDO::getRoleCode, roleCode);
    }

    default List<SysRoleDO> selectListByStatus(Set<Integer> statusSet) {
        return selectList(new LambdaQueryWrapperX<SysRoleDO>()
                .inIfPresent(SysRoleDO::getStatusFlag, statusSet)
        );
    }

    default PageResult<SysRoleDO> selectPage(RolePageReq pageReq) {
        return selectPage(pageReq, new LambdaQueryWrapperX<SysRoleDO>()
                .likeIfPresent(SysRoleDO::getRoleName, pageReq.getRoleName())
                .likeIfPresent(SysRoleDO::getRoleCode, pageReq.getRoleCode())
                .eqIfPresent(SysRoleDO::getStatusFlag, pageReq.getStatusFlag())
                .betweenIfPresent(SysRoleDO::getCreateTime, pageReq.getCreateTime())
                .orderByDesc(SysRoleDO::getCreateTime)
        );
    }

}
