package cn.ibenbeni.bens.message.center.modular.biz.core.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageTemplateContentDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateContentPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageTemplateContentSaveReq;
import java.util.List;
import java.util.Set;

public interface MessageTemplateContentService {

    Long create(MessageTemplateContentSaveReq req);

    void updateById(MessageTemplateContentSaveReq req);

    void deleteById(Long id);

    void deleteByIds(Set<Long> ids);

    MessageTemplateContentDO getById(Long id);

    List<MessageTemplateContentDO> listByTemplateId(Long templateId);

    PageResult<MessageTemplateContentDO> page(MessageTemplateContentPageReq req);

}

