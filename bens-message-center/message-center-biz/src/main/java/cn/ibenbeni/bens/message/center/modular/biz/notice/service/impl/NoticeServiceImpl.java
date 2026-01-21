package cn.ibenbeni.bens.message.center.modular.biz.notice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.api.exception.MessageCenterException;
import cn.ibenbeni.bens.message.center.api.exception.enums.MessageCenterExceptionEnum;
import cn.ibenbeni.bens.message.center.modular.biz.notice.entity.NoticeDO;
import cn.ibenbeni.bens.message.center.modular.biz.notice.mapper.NoticeMapper;
import cn.ibenbeni.bens.message.center.modular.biz.notice.pojo.request.NoticePageReq;
import cn.ibenbeni.bens.message.center.modular.biz.notice.pojo.request.NoticeSaveReq;
import cn.ibenbeni.bens.message.center.modular.biz.notice.service.NoticeService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 通知公告-服务实现类
 */
@Slf4j
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeDO> implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public Long createNotice(NoticeSaveReq req) {
        NoticeDO notice = BeanUtil.toBean(req, NoticeDO.class);
        save(notice);
        return notice.getNoticeId();
    }

    @Override
    public void deleteNotice(Long noticeId) {
        validateNoticeExists(noticeId);
        removeById(noticeId);
    }

    @DSTransactional(rollbackFor = Exception.class)
    @Override
    public void deleteNotice(Set<Long> noticeIds) {
        noticeMapper.deleteByIds(noticeIds);
    }

    @Override
    public void updateNotice(NoticeSaveReq req) {
        validateNoticeExists(req.getNoticeId());
        NoticeDO notice = BeanUtil.toBean(req, NoticeDO.class);
        updateById(notice);
    }

    @Override
    public NoticeDO getNotice(Long noticeId) {
        return getById(noticeId);
    }

    @Override
    public PageResult<NoticeDO> getNoticePage(NoticePageReq pageReq) {
        return noticeMapper.getNoticePage(pageReq);
    }

    private void validateNoticeExists(Long noticeId) {
        NoticeDO notice = getById(noticeId);
        if (notice == null) {
            throw new MessageCenterException(MessageCenterExceptionEnum.NOTICE_NOT_EXIST);
        }
    }

}
