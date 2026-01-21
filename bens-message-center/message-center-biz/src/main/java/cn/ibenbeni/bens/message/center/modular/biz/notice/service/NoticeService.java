package cn.ibenbeni.bens.message.center.modular.biz.notice.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.notice.entity.NoticeDO;
import cn.ibenbeni.bens.message.center.modular.biz.notice.pojo.request.NoticePageReq;
import cn.ibenbeni.bens.message.center.modular.biz.notice.pojo.request.NoticeSaveReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * 通知公告-服务接口
 */
public interface NoticeService extends IService<NoticeDO> {

    /**
     * 创建通知公告
     *
     * @return 通知公告ID
     */
    Long createNotice(NoticeSaveReq req);

    /**
     * 删除通知公告
     *
     * @param noticeId 通知公告ID
     */
    void deleteNotice(Long noticeId);

    /**
     * 批量删除通知公告
     *
     * @param noticeIds 通知公告ID集合
     */
    void deleteNotice(Set<Long> noticeIds);

    /**
     * 修改通知公告
     */
    void updateNotice(NoticeSaveReq req);

    /**
     * 获取通知公告详情
     *
     * @param noticeId 通知公告ID
     */
    NoticeDO getNotice(Long noticeId);

    /**
     * 获取通知公告分页列表
     */
    PageResult<NoticeDO> getNoticePage(NoticePageReq pageReq);

}
