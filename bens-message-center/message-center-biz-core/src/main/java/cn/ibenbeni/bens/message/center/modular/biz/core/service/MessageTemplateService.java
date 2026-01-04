package cn.ibenbeni.bens.message.center.modular.biz.core.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageTemplateDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplatePageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateSaveReq;
import java.util.Set;

public interface MessageTemplateService {

    Long create(MessageTemplateSaveReq req);

    void updateById(MessageTemplateSaveReq req);

    void deleteById(Long id);

    void deleteByIds(Set<Long> ids);

    MessageTemplateDO getById(Long id);

    MessageTemplateDO getByCode(String templateCode);

    PageResult<MessageTemplateDO> page(MessageTemplatePageReq req);

}

