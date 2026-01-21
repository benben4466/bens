package cn.ibenbeni.bens.message.center.modular.biz.message.service.impl;

import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageSendTaskDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.mapper.MessageSendTaskMapper;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageSendTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 消息发送任务 Service 实现
 */
@Service
public class MessageSendTaskServiceImpl implements MessageSendTaskService {

    @Resource
    private MessageSendTaskMapper messageSendTaskMapper;

    @Override
    public boolean save(MessageSendTaskDO task) {
        return messageSendTaskMapper.insert(task) > 0;
    }

    @Override
    public MessageSendTaskDO getById(Long id) {
        return messageSendTaskMapper.selectById(id);
    }

    @Override
    public boolean updateById(MessageSendTaskDO task) {
        return messageSendTaskMapper.updateById(task) > 0;
    }

}
