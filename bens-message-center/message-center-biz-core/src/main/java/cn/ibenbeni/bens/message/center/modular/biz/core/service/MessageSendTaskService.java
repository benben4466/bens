package cn.ibenbeni.bens.message.center.modular.biz.core.service;

import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageSendTaskDO;

/**
 * 消息发送任务 Service
 */
public interface MessageSendTaskService {

    /**
     * 保存任务
     *
     * @param task 任务对象
     * @return 是否成功
     */
    boolean save(MessageSendTaskDO task);

    /**
     * 根据ID获取任务
     *
     * @param id 任务ID
     * @return 任务对象
     */
    MessageSendTaskDO getById(Long id);

    /**
     * 根据ID更新任务
     *
     * @param task 任务对象
     * @return 是否成功
     */
    boolean updateById(MessageSendTaskDO task);

}
