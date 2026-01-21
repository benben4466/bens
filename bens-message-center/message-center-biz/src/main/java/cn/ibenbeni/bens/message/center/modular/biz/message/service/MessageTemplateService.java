package cn.ibenbeni.bens.message.center.modular.biz.message.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageTemplateDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageTemplatePageReq;
import cn.ibenbeni.bens.message.center.modular.biz.message.pojo.request.MessageTemplateSaveReq;

import java.util.Set;

public interface MessageTemplateService {

    Long create(MessageTemplateSaveReq req);

    void updateById(MessageTemplateSaveReq req);

    void deleteById(Long id);

    void deleteByIds(Set<Long> ids);

    MessageTemplateDO getById(Long id);

    MessageTemplateDO getByCode(String templateCode);

    PageResult<MessageTemplateDO> page(MessageTemplatePageReq req);

    /**
     * 根据模板ID、模板编码、渠道类型，检查是否存在
     *
     * @param templateId   模板ID
     * @param templateCode 模板编码
     * @param channelType  渠道类型
     * @return true=存在；false=不存在；
     */
    boolean checkExists(Long templateId, String templateCode, Integer channelType);

}

