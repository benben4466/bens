package cn.ibenbeni.bens.message.center.modular.biz.core.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageTemplateChannelRelDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateChannelRelPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateChannelRelSaveReq;

import java.util.List;
import java.util.Set;

public interface MessageTemplateChannelRelService {

    /**
     * 创建消息模板内容与渠道配置关系
     *
     * @param req 创建信息
     * @return 关系ID
     */
    Long create(MessageTemplateChannelRelSaveReq req);

    /**
     * 批量创建消息模板内容与渠道配置关系
     *
     * @param reqList 创建信息列表
     */
    void createBatch(List<MessageTemplateChannelRelSaveReq> reqList);

    /**
     * 更新消息模板内容与渠道配置关系
     *
     * @param req 更新信息
     */
    void updateById(MessageTemplateChannelRelSaveReq req);

    /**
     * 更新模板内容的渠道关联（先删后增）
     *
     * @param templateContentId 模板内容ID
     * @param channelConfigIds  渠道配置ID集合
     */
    void updateRel(Long templateContentId, Set<Long> channelConfigIds);

    /**
     * 删除消息模板内容与渠道配置关系
     *
     * @param id 关系ID
     */
    void deleteById(Long id);

    /**
     * 批量删除消息模板内容与渠道配置关系
     *
     * @param ids 关系ID集合
     */
    void deleteByIds(Set<Long> ids);

    /**
     * 根据模板内容ID删除关联关系
     *
     * @param templateContentId 模板内容ID
     */
    void deleteByTemplateContentId(Long templateContentId);

    /**
     * 根据模板内容ID集合批量删除关联关系
     *
     * @param templateContentIds 模板内容ID集合
     */
    void deleteByTemplateContentIds(Set<Long> templateContentIds);

    /**
     * 获取消息模板内容与渠道配置关系
     *
     * @param id 关系ID
     * @return 关系详情
     */
    MessageTemplateChannelRelDO getById(Long id);

    /**
     * 分页查询消息模板内容与渠道配置关系
     *
     * @param req 分页查询信息
     * @return 分页结果
     */
    PageResult<MessageTemplateChannelRelDO> page(MessageTemplateChannelRelPageReq req);

}
