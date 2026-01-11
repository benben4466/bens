package cn.ibenbeni.bens.message.center.modular.biz.core.service;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageSendRecordDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageSendRecordCreateReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageSendRecordPageReq;

/**
 * 消息发送记录-Service层
 */
public interface MessageSendRecordService {

    Long create(MessageSendRecordCreateReq req);

    MessageSendRecordDO getById(Long id);

    PageResult<MessageSendRecordDO> page(MessageSendRecordPageReq req);

}
