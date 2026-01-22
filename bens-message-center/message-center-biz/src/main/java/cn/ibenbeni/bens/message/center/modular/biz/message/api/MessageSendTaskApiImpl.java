package cn.ibenbeni.bens.message.center.modular.biz.message.api;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.message.center.api.MessageSendTaskApi;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendTaskDTO;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageSendTaskDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageSendTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 消息发送任务 API 实现
 */
@Service
public class MessageSendTaskApiImpl implements MessageSendTaskApi {

    @Resource
    private MessageSendTaskService messageSendTaskService;

    @Override
    public Long createTask(MessageSendTaskDTO taskDTO) {
        MessageSendTaskDO taskDO = BeanUtil.copyProperties(taskDTO, MessageSendTaskDO.class);
        messageSendTaskService.save(taskDO);
        return taskDO.getTaskId();
    }

    @Override
    public boolean updateTask(MessageSendTaskDTO taskDTO) {
        MessageSendTaskDO taskDO = BeanUtil.copyProperties(taskDTO, MessageSendTaskDO.class);
        return messageSendTaskService.updateById(taskDO);
    }

    @Override
    public MessageSendTaskDTO getTaskById(Long taskId) {
        MessageSendTaskDO taskDO = messageSendTaskService.getById(taskId);
        if (taskDO == null) {
            return null;
        }
        return BeanUtil.copyProperties(taskDO, MessageSendTaskDTO.class);
    }
}
