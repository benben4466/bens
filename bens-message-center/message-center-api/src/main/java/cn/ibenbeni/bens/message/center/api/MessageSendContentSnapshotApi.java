package cn.ibenbeni.bens.message.center.api;

import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendContentSnapshotDTO;

/**
 * 消息发送内容快照 API
 */
public interface MessageSendContentSnapshotApi {

    /**
     * 保存快照
     *
     * @param snapshotDTO 快照信息
     * @return 是否成功
     */
    boolean save(MessageSendContentSnapshotDTO snapshotDTO);

}
