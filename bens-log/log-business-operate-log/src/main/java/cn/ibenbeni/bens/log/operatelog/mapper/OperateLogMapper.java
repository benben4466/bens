package cn.ibenbeni.bens.log.operatelog.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.log.api.pojo.dto.request.OperateLogPageReqDTO;
import cn.ibenbeni.bens.log.operatelog.entity.OperateLogDO;
import cn.ibenbeni.bens.log.operatelog.pojo.request.OperateLogPageReq;

/**
 * 操作日志-Mapper
 */
public interface OperateLogMapper extends BaseMapperX<OperateLogDO> {

    default PageResult<OperateLogDO> selectPage(OperateLogPageReq req) {
        return selectPage(req, new LambdaQueryWrapperX<OperateLogDO>()
                .eqIfPresent(OperateLogDO::getUserId, req.getUserId())
                .eqIfPresent(OperateLogDO::getBizId, req.getBizId())
                .likeIfPresent(OperateLogDO::getModuleNo, req.getModuleNo())
                .likeIfPresent(OperateLogDO::getSubModuleNo, req.getSubModuleNo())
                .likeIfPresent(OperateLogDO::getOpAction, req.getOpAction())
                .betweenIfPresent(OperateLogDO::getCreateTime, req.getCreateTime())
                .orderByDesc(OperateLogDO::getOlgId)
        );
    }

    default PageResult<OperateLogDO> selectPage(OperateLogPageReqDTO reqDTO) {
        return selectPage(reqDTO, new LambdaQueryWrapperX<OperateLogDO>()
                .likeIfPresent(OperateLogDO::getModuleNo, reqDTO.getModuleNo())
                .eqIfPresent(OperateLogDO::getBizId, reqDTO.getBizId())
                .eqIfPresent(OperateLogDO::getUserId, reqDTO.getUserId())
        );
    }

}
