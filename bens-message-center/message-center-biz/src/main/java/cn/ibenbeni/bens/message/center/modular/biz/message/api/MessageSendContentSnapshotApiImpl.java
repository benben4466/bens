package cn.ibenbeni.bens.message.center.modular.biz.message.api;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.message.center.api.MessageSendContentSnapshotApi;
import cn.ibenbeni.bens.message.center.api.domian.dto.MessageSendContentSnapshotDTO;
import cn.ibenbeni.bens.message.center.modular.biz.message.entity.MessageSendContentSnapshotDO;
import cn.ibenbeni.bens.message.center.modular.biz.message.service.MessageSendContentSnapshotService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 消息发送内容快照 API 实现
 */
@Service
public class MessageSendContentSnapshotApiImpl implements MessageSendContentSnapshotApi {

    @Resource
    private MessageSendContentSnapshotService messageSendContentSnapshotService;

    @Override
    public boolean save(MessageSendContentSnapshotDTO snapshotDTO) {
        MessageSendContentSnapshotDO snapshotDO = BeanUtil.copyProperties(snapshotDTO, MessageSendContentSnapshotDO.class);
        boolean success = messageSendContentSnapshotService.save(snapshotDO);
        if (success) {
            snapshotDTO.setId(snapshotDO.getId());
        }
        return success;
    }
}
