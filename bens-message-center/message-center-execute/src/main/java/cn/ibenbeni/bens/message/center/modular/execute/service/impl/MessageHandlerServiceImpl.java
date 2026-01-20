package cn.ibenbeni.bens.message.center.modular.execute.service.impl;

import cn.ibenbeni.bens.message.center.api.MessageSendDetailApi;
import cn.ibenbeni.bens.message.center.api.enums.core.MessageDetailStatusEnum;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageQueuePayload;
import cn.ibenbeni.bens.message.center.api.pojo.dto.MessageSendDetailDTO;
import cn.ibenbeni.bens.message.center.common.chain.ChainProcessor;
import cn.ibenbeni.bens.message.center.modular.execute.action.MessageHandleAction;
import cn.ibenbeni.bens.message.center.modular.execute.idempotent.MessageIdempotentChecker;
import cn.ibenbeni.bens.message.center.modular.execute.model.MessageHandleContext;
import cn.ibenbeni.bens.message.center.modular.execute.service.MessageHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class MessageHandlerServiceImpl implements MessageHandlerService {

    @Resource
    private List<MessageHandleAction> actions;

    @Resource
    private MessageSendDetailApi messageSendDetailApi;

    @Autowired(required = false)
    private MessageIdempotentChecker idempotentChecker;

    /**
     * 责任链处理器
     */
    private ChainProcessor<MessageHandleContext> chainProcessor;

    @PostConstruct
    public void init() {
        // 初始化责任链处理器
        chainProcessor = new ChainProcessor<>(actions, "MessageHandle");
    }

    @Override
    public boolean handleMessage(MessageQueuePayload payload) {
        log.info("[MessageHandlerServiceImpl][开始处理消息][recordId: {}, channelType: {}]", payload.getRecordId(), payload.getChannelType());

        // 幂等性检查
        if (idempotentChecker != null) {
            if (!idempotentChecker.tryAcquire(payload.getRecordId(), payload.getChannelType())) {
                log.info("[MessageHandlerServiceImpl][幂等拦截，跳过处理][recordId: {}]", payload.getRecordId());
                return true; // 返回true，避免MQ重试
            }
        }

        try {
            MessageHandleContext context = buildContext(payload);

            // 执行责任链
            chainProcessor.execute(context);

            if (context.isSuccess()) {
                // 更新 Detail 状态
                MessageSendDetailDTO detail = new MessageSendDetailDTO();
                detail.setId(context.getRecordId());
                detail.setSendStatus(MessageDetailStatusEnum.SUCCESS);
                detail.setOutSerialNumber(String.valueOf(context.getResponseData())); // 假设 ResponseData 是 SerialNum
                detail.setFinishTime(new java.util.Date());
                messageSendDetailApi.updateDetail(detail);

                log.info("[MessageHandlerServiceImpl][消息处理成功][recordId: {}]", context.getRecordId());
                return true;
            } else {
                // 更新 Detail 失败
                MessageSendDetailDTO detail = new MessageSendDetailDTO();
                detail.setId(context.getRecordId());
                detail.setSendStatus(MessageDetailStatusEnum.FAIL);
                detail.setOutResp(context.getFailReason());
                detail.setFinishTime(new java.util.Date());
                messageSendDetailApi.updateDetail(detail);

                log.error("[MessageHandlerServiceImpl][消息处理失败][recordId: {}, failReason: {}]", context.getRecordId(), context.getFailReason());
                return false;
            }

        } catch (Exception ex) {
            log.error("[MessageHandlerServiceImpl][消息处理异常][recordId: {}]", payload.getRecordId(), ex);
            try {
                MessageSendDetailDTO detail = new MessageSendDetailDTO();
                detail.setId(payload.getRecordId());
                detail.setSendStatus(MessageDetailStatusEnum.FAIL);
                detail.setOutResp(ex.getMessage());
                messageSendDetailApi.updateDetail(detail);
            } catch (Exception updateEx) {
                log.error("[MessageHandlerServiceImpl][更新记录状态失败]", updateEx);
            }
            return false;
        }
    }

    private MessageHandleContext buildContext(MessageQueuePayload payload) {
        MessageHandleContext context = new MessageHandleContext();
        context.setRecordId(payload.getRecordId());
        context.setTemplateCode(payload.getTemplateCode());
        context.setTemplateId(payload.getTemplateId());
        context.setChannelType(payload.getChannelType());
        context.setMessageTitle(payload.getMessageTitle());
        context.setMessageContent(payload.getMessageContent());
        context.setRecipient(payload.getRecipient());
        context.setRecipientType(payload.getRecipientType());
        context.setBizType(payload.getBizType());
        context.setBizId(payload.getBizId());
        context.setChannelConfig(payload.getChannelConfig());
        context.setMsgVariables(payload.getMsgVariables());
        context.setRetryCount(payload.getRetryCount());
        context.setTenantId(payload.getTenantId());
        return context;
    }
}