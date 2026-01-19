package cn.ibenbeni.bens.message.center.modular.biz.core.api;

import cn.hutool.core.bean.BeanUtil;
import cn.ibenbeni.bens.message.center.api.MessageSendRecordApi;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendFailTypeEnum;
import cn.ibenbeni.bens.message.center.api.enums.core.MsgSendStatusEnum;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendRecordCreateDTO;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendRecordDTO;
import cn.ibenbeni.bens.message.center.modular.biz.core.entity.MessageSendRecordDO;
import cn.ibenbeni.bens.message.center.modular.biz.core.pojo.request.MessageSendRecordCreateReq;
import cn.ibenbeni.bens.message.center.modular.biz.core.service.MessageSendRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 消息发送记录管理 API 实现
 */
@Service
public class MessageSendRecordApiImpl implements MessageSendRecordApi {

    @Resource
    private MessageSendRecordService messageSendRecordService;

    @Override
    public Long createRecord(MessageSendRecordCreateDTO dto) {
        MessageSendRecordCreateReq req = BeanUtil.toBean(dto, MessageSendRecordCreateReq.class);
        return messageSendRecordService.create(req);
    }

    @Override
    public void updateRecordStatus(Long recordId, MsgSendStatusEnum status, String responseData) {
        messageSendRecordService.updateRecordStatus(recordId, status, responseData);
    }

    @Override
    public void updateRecordFailed(Long recordId, MsgSendFailTypeEnum failType, String failReason) {
        messageSendRecordService.updateRecordFailed(recordId, failType, failReason);
    }

    @Override
    public void increaseRetryCount(Long recordId) {
        messageSendRecordService.increaseRetryCount(recordId);
    }

    @Override
    public MessageSendRecordDTO getById(Long recordId) {
        MessageSendRecordDO entity = messageSendRecordService.getById(recordId);
        if (entity == null) {
            return null;
        }
        MessageSendRecordDTO dto = BeanUtil.toBean(entity, MessageSendRecordDTO.class);
        if (entity.getSendStatus() != null) {
            dto.setSendStatus(entity.getSendStatus().getStatus());
        }
        if (entity.getFailType() != null) {
            dto.setFailType(entity.getFailType().getType());
        }
        if (entity.getRecipientType() != null) {
            dto.setRecipientType(entity.getRecipientType().getType());
        }
        return dto;
    }
}
