package cn.ibenbeni.bens.message.center.modular.biz.message.service;

import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageSendDetailDO;

import java.util.List;

/**
 * 消息发送明细 Service
 */
public interface MessageSendDetailService {

    /**
     * 批量保存明细
     *
     * @param details 明细列表
     * @return 是否成功
     */
    boolean saveBatch(List<MessageSendDetailDO> details);

    /**
     * 根据ID更新明细
     *
     * @param detail 明细对象
     * @return 是否成功
     */
    boolean updateById(MessageSendDetailDO detail);

    /**
     * 根据ID获取明细
     *
     * @param id 明细ID
     * @return 明细对象
     */
    MessageSendDetailDO getById(Long id);

}
