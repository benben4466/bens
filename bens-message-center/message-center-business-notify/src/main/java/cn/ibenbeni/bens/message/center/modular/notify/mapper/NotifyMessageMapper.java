package cn.ibenbeni.bens.message.center.modular.notify.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaUpdateWrapperX;
import cn.ibenbeni.bens.db.api.pojo.query.QueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.message.center.modular.notify.entity.NotifyMessageDO;
import cn.ibenbeni.bens.message.center.modular.notify.pojo.request.NotifyMessageMyPageReq;
import cn.ibenbeni.bens.message.center.modular.notify.pojo.request.NotifyMessagePageReq;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 站内信消息-Mapper
 */
public interface NotifyMessageMapper extends BaseMapperX<NotifyMessageDO> {

    default int updateListRead(Long userId, Integer userType) {
        return update(
                NotifyMessageDO.builder().readStatus(true).readTime(LocalDateTime.now()).build(),
                new LambdaUpdateWrapperX<NotifyMessageDO>()
                        .eq(NotifyMessageDO::getUserId, userId)
                        .eq(NotifyMessageDO::getUserType, userType)
                        .eq(NotifyMessageDO::getReadStatus, false)
        );
    }

    default int updateListRead(Set<Long> messageIdSet, Long userId, Integer userType) {
        return update(
                NotifyMessageDO.builder().readStatus(true).readTime(LocalDateTime.now()).build(),
                new LambdaUpdateWrapperX<NotifyMessageDO>()
                        .in(NotifyMessageDO::getId, messageIdSet)
                        .eq(NotifyMessageDO::getUserId, userId)
                        .eq(NotifyMessageDO::getUserType, userType)
                        .eq(NotifyMessageDO::getReadStatus, false)
        );
    }

    default List<NotifyMessageDO> selectUnreadListByUserIdAndUserType(Long userId, Integer userType, Integer size) {
        return selectList(new QueryWrapperX<NotifyMessageDO>()
                .eq("user_id", userId)
                .eq("user_type", userType)
                .eq("read_status", false)
                .orderByDesc("id")
                .limitN(size)
        );
    }

    default PageResult<NotifyMessageDO> selectPage(NotifyMessagePageReq reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<NotifyMessageDO>()
                .eqIfPresent(NotifyMessageDO::getUserId, reqVO.getUserId())
                .eqIfPresent(NotifyMessageDO::getUserType, reqVO.getUserType())
                .likeIfPresent(NotifyMessageDO::getTemplateCode, reqVO.getTemplateCode())
                .eqIfPresent(NotifyMessageDO::getTemplateType, reqVO.getTemplateType())
                .betweenIfPresent(NotifyMessageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(NotifyMessageDO::getId));
    }

    default PageResult<NotifyMessageDO> selectPage(NotifyMessageMyPageReq reqVO, Long userId, Integer userType) {
        return selectPage(reqVO, new LambdaQueryWrapperX<NotifyMessageDO>()
                .eqIfPresent(NotifyMessageDO::getReadStatus, reqVO.getReadStatus())
                .betweenIfPresent(NotifyMessageDO::getCreateTime, reqVO.getCreateTime())
                .eq(NotifyMessageDO::getUserId, userId)
                .eq(NotifyMessageDO::getUserType, userType)
                .orderByDesc(NotifyMessageDO::getId));
    }

    default Long selectUnreadCountByUserIdAndUserType(Long userId, Integer userType) {
        return selectCount(new LambdaQueryWrapperX<NotifyMessageDO>()
                .eq(NotifyMessageDO::getReadStatus, false)
                .eq(NotifyMessageDO::getUserId, userId)
                .eq(NotifyMessageDO::getUserType, userType));
    }

}
