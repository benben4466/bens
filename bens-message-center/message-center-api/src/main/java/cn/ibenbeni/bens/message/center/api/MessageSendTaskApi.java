package cn.ibenbeni.bens.message.center.api;

import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendTaskDTO;

/**
 * 消息发送任务 API
 */
public interface MessageSendTaskApi {

    /**
     * 创建任务
     *
     * @param taskDTO 任务信息
     * @return 任务ID
     */
    Long createTask(MessageSendTaskDTO taskDTO);

    /**
     * 更新任务
     *
     * @param taskDTO 任务信息
     * @return 是否成功
     */
    boolean updateTask(MessageSendTaskDTO taskDTO);

    /**
     * 获取任务详情
     *
     * @param taskId 任务ID
     * @return 任务信息
     */
    MessageSendTaskDTO getTaskById(Long taskId);

}
