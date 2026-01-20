package cn.ibenbeni.bens.message.center.modular.biz.core.service;

import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageSendContentSnapshotDO;

/**
 * 消息发送内容快照 Service
 */
public interface MessageSendContentSnapshotService {

    /**
     * 保存快照
     *
     * @param snapshot 快照对象
     * @return 是否成功
     */
    boolean save(MessageSendContentSnapshotDO snapshot);

}
