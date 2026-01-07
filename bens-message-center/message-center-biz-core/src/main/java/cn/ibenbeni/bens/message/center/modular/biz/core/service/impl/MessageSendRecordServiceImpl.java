package cn.ibenbeni.bens.message.center.modular.biz.core.service.impl;

import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageSendRecordDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.mapper.MessageSendRecordMapper;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageSendRecordPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageSendRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageSendRecordServiceImpl implements MessageSendRecordService {

    @Resource
    private MessageSendRecordMapper messageSendRecordMapper;

    @Override
    public MessageSendRecordDO getById(Long id) {
        return messageSendRecordMapper.selectById(id);
    }

    @Override
    public PageResult<MessageSendRecordDO> page(MessageSendRecordPageReq req) {
        return messageSendRecordMapper.page(req);
    }
}
