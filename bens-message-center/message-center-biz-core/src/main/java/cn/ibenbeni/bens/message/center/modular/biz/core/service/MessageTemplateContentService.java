package cn.ibenbeni.bens.message.center.modular.biz.core.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageTemplateContentDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateContentPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateContentSaveReq;
import java.util.List;
import java.util.Set;

public interface MessageTemplateContentService {

    /**
     * 创建消息模板内容
     *
     * @param req 创建信息
     * @return 模板内容ID
     */
    Long create(MessageTemplateContentSaveReq req);

    /**
     * 批量创建消息模板内容
     *
     * @param reqList 创建信息列表
     */
    void createBatch(List<MessageTemplateContentSaveReq> reqList);

    /**
     * 更新消息模板内容
     *
     * @param req 更新信息
     */
    void updateById(MessageTemplateContentSaveReq req);

    /**
     * 删除消息模板内容
     *
     * @param id 模板内容ID
     */
    void deleteById(Long id);

    /**
     * 批量删除消息模板内容
     *
     * @param ids 模板内容ID集合
     */
    void deleteByIds(Set<Long> ids);

    /**
     * 根据模板ID删除所有内容
     *
     * @param templateId 模板ID
     */
    void deleteByTemplateId(Long templateId);

    /**
     * 根据模板ID集合批量删除所有内容
     *
     * @param templateIds 模板ID集合
     */
    void deleteByTemplateIds(Set<Long> templateIds);

    /**
     * 获取消息模板内容详情
     *
     * @param id 模板内容ID
     * @return 模板内容详情
     */
    MessageTemplateContentDO getById(Long id);

    /**
     * 根据模板ID获取内容列表
     *
     * @param templateId 模板ID
     * @return 内容列表
     */
    List<MessageTemplateContentDO> listByTemplateId(Long templateId);

    /**
     * 根据模板ID集合获取内容列表
     *
     * @param templateIds 模板ID集合
     * @return 内容列表
     */
    List<MessageTemplateContentDO> listByTemplateIds(Set<Long> templateIds);

    /**
     * 分页查询消息模板内容
     *
     * @param req 分页查询信息
     * @return 分页结果
     */
    PageResult<MessageTemplateContentDO> page(MessageTemplateContentPageReq req);

    /**
     * 根据模板ID和渠道类型查询内容
     *
     * @param templateId  模板ID
     * @param channelType 渠道类型
     * @return 模板内容
     */
    MessageTemplateContentDO getByTemplateIdAndChannelType(Long templateId, Integer channelType);

}

