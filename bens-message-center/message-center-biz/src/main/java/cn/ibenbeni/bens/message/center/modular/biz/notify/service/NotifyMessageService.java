package cn.ibenbeni.bens.message.center.modular.biz.notify.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.notify.entity.NotifyMessageDO;
import cn.ibenbeni.bens.message.center.modular.biz.notify.entity.NotifyTemplateDO;
import cn.ibenbeni.bens.message.center.modular.biz.notify.pojo.request.NotifyMessageMyPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.notify.pojo.request.NotifyMessagePageReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 站内信消息-服务
 */
public interface NotifyMessageService extends IService<NotifyMessageDO> {

    /**
     * 创建站内信消息
     *
     * @param userId          用户ID
     * @param userType        用户类型
     * @param template        站内信模版
     * @param templateContent 站内信模版格式化后内容
     * @param templateParams  站内信参数
     * @return 站内信消息ID
     */
    Long createNotifyMessage(Long userId, Integer userType, NotifyTemplateDO template, String templateContent, Map<String, Object> templateParams);

    /**
     * 标记站内信为已读
     *
     * @param messageIdSet 站内信消息ID集合
     * @param userId       用户ID
     * @param userType     用户类型
     * @return 更新数量
     */
    int updateNotifyMessageRead(Set<Long> messageIdSet, Long userId, Integer userType);

    /**
     * 标记所有站内信为已读
     *
     * @param userId   用户ID
     * @param userType 用户类型
     * @return 更新数量
     */
    int updateAllNotifyMessageRead(Long userId, Integer userType);

    /**
     * 获得站内信消息
     *
     * @param messageId 站内信消息ID
     */
    NotifyMessageDO getNotifyMessage(Long messageId);

    /**
     * 获得[我的]未读站内信消息
     *
     * @param userId   用户ID
     * @param userType 用户类型
     * @param size     获取数量
     */
    List<NotifyMessageDO> listUnreadNotifyMessage(Long userId, Integer userType, Integer size);

    /**
     * 分页查询站内信消息
     */
    PageResult<NotifyMessageDO> pageNotifyMessage(NotifyMessagePageReq pageReq);

    /**
     * 获得[我的]站内信分页
     *
     * @param pageReq  分页参数
     * @param userId   用户ID
     * @param userType 用户类型
     */
    PageResult<NotifyMessageDO> pageMyNotifyMessage(NotifyMessageMyPageReq pageReq, Long userId, Integer userType);

    /**
     * 统计用户未读站内信条数
     *
     * @param userId   用户ID
     * @param userType 用户类型
     * @return 返回未读站内信条数
     */
    Long getUnreadNotifyMessageCount(Long userId, Integer userType);

}
