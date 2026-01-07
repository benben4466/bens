package cn.ibenbeni.bens.message.center.modular.biz.core.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageSendRecordDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageSendRecordPageReq;

public interface MessageSendRecordService {

    MessageSendRecordDO getById(Long id);

    PageResult<MessageSendRecordDO> page(MessageSendRecordPageReq req);
}
