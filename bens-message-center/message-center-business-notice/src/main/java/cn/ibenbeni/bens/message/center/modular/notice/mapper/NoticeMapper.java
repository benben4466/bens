package cn.ibenbeni.bens.message.center.modular.notice.mapper;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.db.api.pojo.query.LambdaQueryWrapperX;
import cn.ibenbeni.bens.db.mp.mapper.BaseMapperX;
import cn.ibenbeni.bens.message.center.modular.notice.entity.NoticeDO;
import cn.ibenbeni.bens.message.center.modular.notice.pojo.request.NoticePageReq;

/**
 * 通知公告-Mapper
 */
public interface NoticeMapper extends BaseMapperX<NoticeDO> {

    default PageResult<NoticeDO> getNoticePage(NoticePageReq pageReq) {
        return selectPage(pageReq, new LambdaQueryWrapperX<NoticeDO>()
                .likeIfPresent(NoticeDO::getNoticeTitle, pageReq.getNoticeTitle())
                .eqIfPresent(NoticeDO::getNoticeType, pageReq.getNoticeType())
                .eqIfPresent(NoticeDO::getStatusFlag, pageReq.getStatusFlag())
                .orderByDesc(NoticeDO::getCreateTime)
        );
    }

}
