package cn.ibenbeni.bens.message.center.modular.biz.core.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageChannelConfigDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageChannelConfigPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageChannelConfigSaveReq;

import java.util.Set;

public interface MessageChannelConfigService {

    Long create(MessageChannelConfigSaveReq req);

    void updateById(MessageChannelConfigSaveReq req);

    void deleteById(Long id);

    void deleteByIds(Set<Long> ids);

    MessageChannelConfigDO getById(Long id);

    PageResult<MessageChannelConfigDO> page(MessageChannelConfigPageReq req);
}
