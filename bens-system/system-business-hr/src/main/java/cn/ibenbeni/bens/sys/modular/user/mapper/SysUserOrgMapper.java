package cn.ibenbeni.bens.sys.modular.user.mapper;

import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.rule.enums.YesOrNotEnum;
import cn.ibenbeni.bens.sys.modular.user.entity.SysUserOrgDO;

import java.util.Set;

/**
 * 用户组织机构关联 Mapper 接口
 *
 * @author: benben
 * @time: 2025/7/8 下午9:58
 */
public interface SysUserOrgMapper extends BaseMapperX<SysUserOrgDO> {

    /**
     * 获取用户主组织数量
     *
     * @param userId 用户ID
     * @return 用户主组织数量
     */
    default Long getMainOrgCount(Long userId, Long userOrgId) {
        return selectCount(new LambdaQueryWrapperX<SysUserOrgDO>()
                .eqIfPresent(SysUserOrgDO::getUserOrgId, userOrgId)
                .eq(SysUserOrgDO::getUserId, userId)
                .eq(SysUserOrgDO::getMainFlag, YesOrNotEnum.Y.getCode())
        );
    }

    default Long selectCountByOrgId(Long orgId) {
        return selectCount(new LambdaQueryWrapperX<SysUserOrgDO>()
                .eq(SysUserOrgDO::getOrgId, orgId)
        );
    }

    default Long selectCountByPositionId(Long positionId) {
        return selectCount(new LambdaQueryWrapperX<SysUserOrgDO>()
                .eq(SysUserOrgDO::getPositionId, positionId)
        );
    }

    /**
     * 判断用户是否已任职
     *
     * @param userId 用户ID
     * @param positionId 职位ID
     * @return 是否已任职
     */
    default Boolean userPositionExisted(Long userId, Long positionId) {
        return selectCount(new LambdaQueryWrapperX<SysUserOrgDO>()
                .eq(SysUserOrgDO::getUserId, userId)
                .eq(SysUserOrgDO::getPositionId, positionId)
        ) > 0;
    }

    default int deleteByUserIds(Set<Long> userIds) {
        return delete(new LambdaQueryWrapperX<SysUserOrgDO>()
                .in(SysUserOrgDO::getUserId, userIds)
        );
    }

    default int deleteByOrgIds(Set<Long> orgIds) {
        return delete(new LambdaQueryWrapperX<SysUserOrgDO>()
                .in(SysUserOrgDO::getOrgId, orgIds)
        );
    }

    default int deleteByPositionIds(Set<Long> positionIds) {
        return delete(new LambdaQueryWrapperX<SysUserOrgDO>()
                .in(SysUserOrgDO::getPositionId, positionIds)
        );
    }

}
