package cn.ibenbeni.bens.sys.modular.position.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.sys.modular.position.entity.SysPositionDO;
import cn.ibenbeni.bens.sys.modular.position.pojo.request.PositionPageReq;

import java.util.List;
import java.util.Set;

/**
 * 职位信息 Mapper 接口
 *
 * @author: benben
 * @time: 2025/7/12 下午1:37
 */
public interface SysPositionMapper extends BaseMapperX<SysPositionDO> {

    default SysPositionDO selectByName(String positionName) {
        return selectOne(SysPositionDO::getPositionName, positionName);
    }

    default SysPositionDO selectByCode(String positionCode) {
        return selectOne(SysPositionDO::getPositionCode, positionCode);
    }

    default List<SysPositionDO> selectList(Set<Long> positionIds, Set<Integer> statusFlags) {
        return selectList(new LambdaQueryWrapperX<SysPositionDO>()
                .inIfPresent(SysPositionDO::getStatusFlag, statusFlags)
                .inIfPresent(SysPositionDO::getPositionId, positionIds)
        );
    }

    default PageResult<SysPositionDO> selectPage(PositionPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<SysPositionDO>()
                .likeIfPresent(SysPositionDO::getPositionName, req.getPositionName())
                .likeIfPresent(SysPositionDO::getPositionCode, req.getPositionCode())
                .eqIfPresent(SysPositionDO::getStatusFlag, req.getStatusFlag())
                .orderByDesc(SysPositionDO::getPositionSort)
                .orderByDesc(SysPositionDO::getCreateTime)
        );
    }

}
