package cn.ibenbeni.bens.message.center.modular.biz.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.db.api.pojo.page.PageResult;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageSendRecordDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.mapper.MessageSendRecordMapper;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageSendRecordCreateReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageSendRecordPageReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageSendRecordService;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class MessageSendRecordServiceImpl implements MessageSendRecordService {

    @Resource
    private MessageSendRecordMapper messageSendRecordMapper;

    @Resource
    private MessageTemplateService messageTemplateService;

    @Override
    public Long create(MessageSendRecordCreateReq req) {
        boolean templateExists = messageTemplateService.checkExists(req.getTemplateId(), req.getTemplateCode(), req.getChannelType());
        if (!templateExists) {
            // TODO 记录入库
            log.error("[create][新增消息发送记录失败][入参: {}]", req);
            return null;
        }

        MessageSendRecordDO entity = BeanUtil.toBean(req, MessageSendRecordDO.class);
        messageSendRecordMapper.insert(entity);
        return entity.getRecordId();
    }

    @Override
    public MessageSendRecordDO getById(Long id) {
        return messageSendRecordMapper.selectById(id);
    }

    @Override
    public PageResult<MessageSendRecordDO> page(MessageSendRecordPageReq req) {
        return messageSendRecordMapper.page(req);
    }

}
