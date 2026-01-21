package cn.ibenbeni.bens.message.center.modular.biz.message.service.impl;

import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageSendContentSnapshotDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.mapper.MessageSendContentSnapshotMapper;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageSendContentSnapshotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 消息发送内容快照 Service 实现
 */
@Service
public class MessageSendContentSnapshotServiceImpl implements MessageSendContentSnapshotService {

    @Resource
    private MessageSendContentSnapshotMapper messageSendContentSnapshotMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(MessageSendContentSnapshotDO snapshot) {
        return messageSendContentSnapshotMapper.insert(snapshot) > 0;
    }

}
